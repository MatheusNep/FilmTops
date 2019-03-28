package projetofragmento.cursoandroid.com.filmstops;


import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


public class FragmentMain extends Fragment {
    private ArrayAdapter<Filme> dadosAdapter;
    private ListView listView;


    public FragmentMain() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.fragment_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.filter_id){

            FilmTask task = new FilmTask();
            task.execute();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootview = inflater.inflate(R.layout.fragment_main, container, false);

       listView = rootview.findViewById(R.id.lv_main);

        FilmTask task = new FilmTask();
        dadosAdapter = new FilmeAdapter(getActivity(), new ArrayList<Filme>());
        task.execute();

        listView.setAdapter(dadosAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                intent.putExtra("title", dadosAdapter.getItem(position).getTitle());
                intent.putExtra("url", dadosAdapter.getItem(position).getUrlPoster());
                intent.putExtra("overview", dadosAdapter.getItem(position).getOverview());
                intent.putExtra("popularity", dadosAdapter.getItem(position).getPopularity());
                intent.putExtra("votes", dadosAdapter.getItem(position).getVoteCount());
                startActivity(intent);
            }
        });


        return rootview;
    }

    public class FilmTask extends AsyncTask<Void, Void, ArrayList<Filme>> {
        private final String LOG_TAG = FilmTask.class.getSimpleName();
        public ArrayList<Filme> arrayList =  new ArrayList();

        private ArrayList getDataFromJson(String jsonStr)
                throws JSONException {

            // These are the names of the JSON objects that need to be extracted.
            final String OWM_COMP = "results";
            final String OWM_TITLE = "title";
            final String OWM_POSTER = "poster_path";
            final String OWM_ID = "id";
            final String OWM_VOTE = "vote_count";
            final String OWM_POP = "popularity";
            final String OWM_OVER = "overview";

            JSONObject forecastJson = new JSONObject(jsonStr);
            JSONArray filmArray = forecastJson.getJSONArray(OWM_COMP);

            for(int i = 0; i < filmArray.length(); i++) {

                JSONObject dayForecast = filmArray.getJSONObject(i);
                Filme filme = new Filme();
                filme.setTitle(dayForecast.getString(OWM_TITLE));
                filme.setUrlPoster(dayForecast.getString(OWM_POSTER));
                filme.setId(dayForecast.getInt(OWM_ID));
                filme.setVoteCount(dayForecast.getLong(OWM_VOTE));
                filme.setPopularity(dayForecast.getDouble(OWM_POP));
                filme.setOverview(dayForecast.getString(OWM_OVER));
                arrayList.add(filme);
            }

            for (Filme s : arrayList) {
                Log.v(LOG_TAG, "Forecast entry: " + s);
            }
            return arrayList;

        }

        @Override
        protected ArrayList doInBackground(Void... voids) {
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String jsonStr = null;

            try {
                final String base = "http://api.themoviedb.org/3/movie/now_playing?";
                final String apiKey = "api_key";
                final String language = "language";
                final String genres = "genres";

               // http://api.themoviedb.org/3/movie/now_playing?language=en&api_key=f743249029c6219048231659db6e47fd&genres=now-playing

                Uri builduri = Uri.parse(base).buildUpon()
                        .appendQueryParameter(language, "en")
                        .appendQueryParameter(genres, "now-playing")
                        .appendQueryParameter(apiKey, BuildConfig.API_KEY)
                        .build();
                URL url = new URL(builduri.toString());

                Log.v(LOG_TAG, "Built URI " + builduri.toString());

                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();

                if(inputStream == null){
                    return null;
                }

                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    // Stream was empty.  No point in parsing.
                    return null;
                }
                jsonStr = buffer.toString();
                Log.i("alomarciano", jsonStr);

            } catch (IOException e) {
                Log.e(LOG_TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attemping
                // to parse it.
                return null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(LOG_TAG, "Error closing stream", e);
                    }
                }
            }

            try{
                return getDataFromJson(jsonStr);
            } catch (JSONException e) {
                Log.e(LOG_TAG, e.getMessage(), e);
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<Filme> strings) {
            if(strings != null) {
                dadosAdapter.clear();
                for (int i = 0; i < strings.size(); i++) {
                    dadosAdapter.add(strings.get(i));
                }
            }
        }

    }
}

