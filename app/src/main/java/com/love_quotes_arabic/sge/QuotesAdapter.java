package com.love_quotes_arabic.sge;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class QuotesAdapter extends RecyclerView.Adapter<QuotesAdapter.QuotesViewHolder> {

    List<String> quotes = null;
    Context context;

    public QuotesAdapter(List<String> quotes, Context context) {
        this.quotes = quotes;
        this.context = context;
    }

    @NonNull
    @Override
    public QuotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_item_quote, parent, false);
        return new QuotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuotesViewHolder holder, int position) {
        //String[] colors = {"#99448AAA", "#99FF0000", "#990000FF", "#99FFFF00"};
        String quote = quotes.get(position);
        holder.txtQuote.setText(quote);
       // int color = position % colors.length;
        //int intColor = Color.parseColor(colors[color]);
        //holder.quoteContainer.setBackgroundColor(intColor);

        //Whatsapp share
        holder.whatsapp.setOnClickListener(v ->
        {
            try {
                Intent whatsappshare = new Intent(Intent.ACTION_SEND);
                whatsappshare.setType("text/plane");
                whatsappshare.putExtra(Intent.EXTRA_TEXT,quotes.get(position));
                whatsappshare.setPackage("com.whatsapp");
                context.startActivity(whatsappshare);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        //end here


        //copy button
        holder.copybutton.setOnClickListener(v ->{
            ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
            ClipData data =(ClipData) ClipData.newPlainText("text",quotes.get(position));
            clipboardManager.setPrimaryClip(data);

            Toast.makeText(context, "تم نسخ الاقتباس إلى الحافظة", Toast.LENGTH_SHORT).show();
        });
        //end here

        //shatebutton
        holder.sharebutton.setOnClickListener(v ->{
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("text/plane");
            share.putExtra(Intent.EXTRA_TEXT,quotes.get(position));
            context.startActivity(share);
            //end here
        });

    }


    @Override
    public int getItemCount() {
        return quotes.size();
    }

    public class QuotesViewHolder extends RecyclerView.ViewHolder{

        TextView txtQuote;
        LinearLayout quoteContainer;
        private ImageView whatsapp, sharebutton, copybutton;

        public QuotesViewHolder(@NonNull View itemView) {
            super(itemView);

            txtQuote = itemView.findViewById(R.id.txtquote);
            quoteContainer = itemView.findViewById(R.id.quoteContainer);
            whatsapp = itemView.findViewById(R.id.whatsapp);
            sharebutton = itemView.findViewById(R.id.sharebutton);
            copybutton = itemView.findViewById(R.id.copybotton);

        }
    }
}
