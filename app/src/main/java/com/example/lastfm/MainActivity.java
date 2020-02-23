package com.example.lastfm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView;
        recyclerView = findViewById(R.id.RecyclerView);
        recyclerView.adapter = Adapter(generateFakeNews())
        rectclerView.layoutManager = LinearLayoutManager(this)
    }

    private fun generateFakeNews(): List<String>{
        val values = mutableListOf<String>()
                for(i in 0..100){
                    values.add("$i element")
        }
        return values
    }



    class Adapter(private val values: List<String>): RecyclerView.Adapter<Adapter.ViewHolder>() {
        @Override fun getItemCount()= values.size

        @Override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int){
            val itemView = LaoutInflater.from(parent?.context).inflate(R.layout.list_item_view, parent, false)
                    return ViewHolder(itemView)
        }

        @Override fun onBindViewHolder(holder: ViewHolder?, position Int){
            holder?.textView?.text = values[position]
        }

        Class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView){
            var textView: TextView? = null
                init{
                textView = itemView?.findViewById(R.id.text_list_item)
            }
            }
        }
    }