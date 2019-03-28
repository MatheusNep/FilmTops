package projetofragmento.cursoandroid.com.filmstops;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class FilmeAdapter extends ArrayAdapter<Filme> {
    private Context context;
    private ArrayList<Filme> filme;


    public FilmeAdapter( Context c, ArrayList<Filme> objects) {
        super(c, 0, objects);
        this.context = c;
        this.filme= objects;


    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       View view = convertView;

        if (view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.list_iten_film,parent, false);
        }
        if (filme.size()>0){
            ImageView imageView = view.findViewById(R.id.iv_filmes);
            Filme filmeObject = filme.get(position);
            Log.i("123", ""+filmeObject.getUrlPoster());
            Picasso.get().load("https://image.tmdb.org/t/p/w500/"+filmeObject.getUrlPoster()).into(imageView);


        }
        return view;

    }
}
