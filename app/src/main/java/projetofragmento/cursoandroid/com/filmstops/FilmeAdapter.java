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

            view = inflater.inflate(R.layout.fragment_main,parent, false);
        }
        if (filme.size()>0){
            ImageView imageView = view.findViewById(R.id.im_post_list);
            Filme filmeObject = filme.get(position);

//            imageView.setImageURI(Uri.parse("https://image.tmdb.org/t/p/w500/"+filmeObject.getUrlPoster()));
//            //parseObject.getParseFile("image");
//            Picasso.with(context).load().getUrl().fit().into(imageView);
            Picasso.get().load("https://image.tmdb.org/t/p/w500/"+filmeObject.getUrlPoster()).into(imageView);
            Log.i("alomarciano2", filmeObject.toString());
        }
        return view;

    }
}
