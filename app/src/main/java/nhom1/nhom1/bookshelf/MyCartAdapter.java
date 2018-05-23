package nhom1.nhom1.bookshelf;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dicol on 23/05/2018.
 */

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.ViewHolder> {
    private ArrayList<Book> book;
    private Context context;
    public MyCartAdapter(ArrayList<Book> book,Context context){
        this.book = book;
        this.context = context;
     }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_my_cart, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
     Glide.with(context).load(book.get(position).getImage()).into(holder.BookPhoto);
     holder.BookName.setText(book.get(position).getName());
     holder.BookAuthor.setText(book.get(position).getAuthor());
    }

    @Override
    public int getItemCount() {
        if (book!=null && !book.isEmpty()){
            return book.size();
        } else {
            return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView BookPhoto;
        public TextView BookName;
        public TextView BookAuthor;
        public ViewHolder(View itemView) {
            super(itemView);
            BookPhoto = itemView.findViewById(R.id.imageView_book_photo);
            BookName = itemView.findViewById(R.id.textViewBookName);
            BookAuthor= itemView.findViewById(R.id.textAuthorName);


        }
    }
}
