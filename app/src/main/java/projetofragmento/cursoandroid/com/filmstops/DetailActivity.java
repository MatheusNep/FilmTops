package projetofragmento.cursoandroid.com.filmstops;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private ImageView poster;
    private TextView titleView;
    private TextView popularityView;
    private TextView votesView;
    private TextView overviewView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        poster = findViewById(R.id.iv_poster);
        titleView = findViewById(R.id.tv_title);
        popularityView = findViewById(R.id.tv_popularity);
        votesView = findViewById(R.id.tv_votes);
        overviewView = findViewById(R.id.tv_overview);

        Bundle extra = getIntent().getExtras();
        String title = extra.getString("title");
        String url = extra.getString("url");
        Double popularity = extra.getDouble("popularity");
        Long votes = extra.getLong("votes");
        String overview = extra.getString("overview");

        titleView.setText(title);
        popularityView.setText("Popularity: "+popularity);
        votesView.setText("Votes: "+votes);
        overviewView.setText("  "+overview);
        Picasso.get().load("https://image.tmdb.org/t/p/w500/"+url).into(poster);

    }




}
