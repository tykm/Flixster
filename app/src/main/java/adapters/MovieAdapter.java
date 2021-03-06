package adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.flixster.MovieDetailsActivity;
import com.example.flixster.R;
import com.example.flixster.databinding.ItemMovieBinding;
import com.example.flixster.models.Movie;

import org.parceler.Parcels;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private ItemMovieBinding binding;
    Context context;
    List<Movie> movies;

    public MovieAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    // Usually involves inflating a layout from XML and returning the holder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("MovieAdapter", "onCreateViewHolder");
        View movieView = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new ViewHolder(movieView);
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d("MovieAdapter", "onBindViewHolder " + position);
        // Get the movie at the passed in position
        Movie movie = movies.get(position);
        // Bind the movie data into the ViewHolder
        holder.bind(movie);
        holder.tvTitle.setTextColor(Color.WHITE);
        holder.tvOverview.setTextColor(Color.WHITE);
    }

    // Returns the total count of items in the list
    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tvTitle;
        TextView tvOverview;
        ImageView ivPoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvOverview = itemView.findViewById(R.id.tvOverview);
            ivPoster = itemView.findViewById(R.id.ivPoster);
            // add this viewholder as the itemView's OnClickListener
            itemView.setOnClickListener(this);
            // Since the layout was already inflated within onCreateViewHolder(), we
            // can use this bind() method to associate the layout variables
            // with the layout.
            binding = ItemMovieBinding.bind(itemView);
        }

        public void bind(Movie movie) {
            tvTitle.setText(movie.getTitle());
            tvOverview.setText(movie.getOverview());
            String imageUrl;
            // If phone in landscape then load backdropimage
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                imageUrl = movie.getBackdropPath();
            }
            // Else, load poster image
            else {
                imageUrl = movie.getPosterPath();
            }
            Glide.with(context)
                    .load(imageUrl)
                    .into(ivPoster);
        }

        // When the user clicks on a row, show MovieDetailsActivity for the selected movie
        @Override
        public void onClick(View view) {
            // Get item position
            int position = getAdapterPosition();
            // Make sure the position exists
            if (position != RecyclerView.NO_POSITION) {
                // Get the movie at the position
                Movie movie = movies.get(position);
                // Create intent for the new detail activity
                Intent i = new Intent(context, MovieDetailsActivity.class);
                // Serialize the movie using Parceler using the SimpleName as the key
                i.putExtra(Movie.class.getSimpleName(), Parcels.wrap(movie));
                // Show the activity
                context.startActivity(i);
            }

        }
    }
}
