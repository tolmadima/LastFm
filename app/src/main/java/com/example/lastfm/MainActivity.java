package com.example.lastfm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import com.example.lastfm.NewsData;
import com.example.lastfm.NewsAdapter;


import java.util.Arrays;
import java.util.Collection;

public class MainActivity extends AppCompatActivity {
    private ImageView newsImage;
    private TextView textHeader;
    private TextView textDescription;


    private RecyclerView newsRecyclerView;
    private NewsAdapter newsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initRecyclerView();

        loadNews();
    }

        private void loadNews(){
            Collection<NewsData> news = getNews();
            newsAdapter.setItems(news);
        }


        private Collection<NewsData> getNews(){
           return Arrays.asList(
                   new NewsData("14 марта пройдет Зонг опера в память умершего солиста группы 'Король и Шут' Михаила Горшнева","https://sun9-54.userapi.com/c855128/v855128486/1dd25e/7hb91ciRebM.jpg","Билеты можно купить по ссылке http://biletniystol.ru/rostov-na-donu/rock-musical-todd-bilety-14-03-2020"),
                   new NewsData("Тилль Линдеманн показал клип своего нового проекта Na chui",
                           "https://i0.wp.com/sova.ponominalu.ru/wp-content/uploads/2020/02/lindemann-scaled.jpg?resize=1920%2C1280&ssl=1",
                           "Артист экспериментирует с танцевальной музыкой и психикой фанатов. Тилль Линдеманн продолжает выпускать провокационные видео. В предыдущем ролике «Platz Eins», выпущенном проектом Lindemann, все самые откровенные сцены подверглись цензуре. Однако теперь можно увидеть и полную версию — она доступна на немецком сайте Vizit-X за деньги. Кадры использовались для видео на песню «Till the End», которую Линдеманн выпустил от лица своего нового проекта с понятным каждому русскому человеку названием Na Chui. В нём Тилль экспериментирует с танцевальной музыкой. Если вы не хотите смотреть откровенное видео, трек можно послушать на стримингах"),
                   new NewsData("Новый альбом группы 'Пошлая Молли'","https://primamedia.gcdn.co/f/main/1620/1619681.jpg?46d61d2800a44f1a45a123006fb898d8",
                           "«Пошлая Мо́лли» — украинская поп-панк-группа, основанная музыкантом из Харькова Кириллом Тимошенко, более известным под псевдонимом Кирилл Бледный. Группа является одним из самых ярких представителей синти-панка на Украине, совмещая поп-панк с электронной музыкой. Лидер группы говорит о «Пошлой Молли» как о собирательном образе испорченной школьницы. Кирилл не сказал ничего конкретного по поводу альбома, но сказал что он будет все в том же стиле, что и предыдущие."),
                   new NewsData("ZXc","https://www.w3schools.com/w3images/lights.jpg","LUL"),
                   new NewsData("Limp Bizkit сыграли долгожданный концерт в Москве","https://sub-cult.ru/images/2019/ESluch/Limp_Bizkit.jpg","О, Фред! Это было прекрасно! 22 февраля в Москве прошел бомбический концерт группы Limp Bizkit в Мегаспорте.")
           );
        }
    private void initRecyclerView() {
        newsRecyclerView = findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsAdapter = new NewsAdapter();
        newsRecyclerView.setAdapter(newsAdapter);
    }







}