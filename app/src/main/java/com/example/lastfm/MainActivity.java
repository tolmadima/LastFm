package com.example.lastfm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.icu.text.Edits;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;

public class MainActivity extends AppCompatActivity {



    private static final String APP_ID = "b4ab3bf82dcb495e182e04cfc1f12b7b";


    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String url = String.format("http://ws.audioscrobbler.com/2.0/?method=chart.gettopartists&limit=1&api_key=b4ab3bf82dcb495e182e04cfc1f12b7b&format=json");

        initRecyclerView();
        loadArtists();
        TextView textView = findViewById(R.id.asynctask_view);
        new GetTopTask(textView).execute(url);
    }

    private static class GetTopTask extends AsyncTask<String, Void, String> {
        private TextView tvTop;

        GetTopTask(TextView textView) {
            this.tvTop = textView;
        }
        @Override
        protected String doInBackground(String... strings) {
            String artistName = "UNDEFINED";
            try {
                URL url = new URL(strings[0]);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

                InputStream stream = new BufferedInputStream(urlConnection.getInputStream());
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(stream));
                StringBuilder builder = new StringBuilder();

                String inputString;
                while ((inputString = bufferedReader.readLine()) != null) {
                    builder.append(inputString);
                }

                JSONObject topLevel = new JSONObject(builder.toString());
                JSONObject main = topLevel.getJSONObject("artists");
                System.out.println(main);
                JSONArray artist = (JSONArray) main.get("artist");
                System.out.println(artist);


                urlConnection.disconnect();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return artistName;
        }

        @Override
        protected void onPostExecute(String artistName) {
            tvTop.setText("Top artist:" + artistName);
        }
    }

        private void loadArtists(){
            Collection<NewsData> news = getArtists();
            newsAdapter.setItems(news);
        }

        private Collection<NewsData> getArtists(){
           return Arrays.asList(
                   new NewsData("Тилль Линдеманн показал клип своего нового проекта Na chui", "https://i0.wp.com/sova.ponominalu.ru/wp-content/uploads/2020/02/lindemann-scaled.jpg?resize=1920%2C1280&ssl=1", "Артист экспериментирует с танцевальной музыкой и психикой фанатов. Тилль Линдеманн продолжает выпускать провокационные видео. В предыдущем ролике «Platz Eins», выпущенном проектом Lindemann, все самые откровенные сцены подверглись цензуре. Однако теперь можно увидеть и полную версию — она доступна на немецком сайте Vizit-X за деньги. Кадры использовались для видео на песню «Till the End», которую Линдеманн выпустил от лица своего нового проекта с понятным каждому русскому человеку названием Na Chui. В нём Тилль экспериментирует с танцевальной музыкой. Если вы не хотите смотреть откровенное видео, трек можно послушать на стримингах")
//                   new NewsData("Новый альбом группы 'Пошлая Молли'","https://primamedia.gcdn.co/f/main/1620/1619681.jpg?46d61d2800a44f1a45a123006fb898d8", "«Пошлая Мо́лли» — украинская поп-панк-группа, основанная музыкантом из Харькова Кириллом Тимошенко, более известным под псевдонимом Кирилл Бледный. Группа является одним из самых ярких представителей синти-панка на Украине, совмещая поп-панк с электронной музыкой. Лидер группы говорит о «Пошлой Молли» как о собирательном образе испорченной школьницы. Кирилл не сказал ничего конкретного по поводу альбома, но сказал что он будет все в том же стиле, что и предыдущие."),
//                   new NewsData("Рецензия: Artik & Asti - «7 (Part 2)»","https://cdn1.intermedia.ru/img/news/344772.jpg","Неочевидное разбиение альбома «7» на две части оказалось вполне выигрышной стратегией. В каждый из дисков вошло по семь песен, и Artik & Asti рассчитали так, чтобы потенциальные хиты занимали больше половины трек-листа. Поэтому первая часть пластинки «7» заканчивалась примерно в тот момент, когда слушатель начинал замечать за музыкантами «повторение ходов». За без малого год, прошедший с момента выхода первой части, выстрелили не все содержавшиеся там треки, зато «Грустный дэнс», можно сказать, выстрелил за всех, став одним из главных шлягеров 2019 года и собрав разнообразные призы и премии."),
//                   new NewsData("Limp Bizkit сыграли долгожданный концерт в Москве","https://sub-cult.ru/images/2019/ESluch/Limp_Bizkit.jpg","О, Фред! Это было прекрасно! 22 февраля в Москве прошел бомбический концерт группы Limp Bizkit в Мегаспорте."),
//                   new NewsData("Рецензия: Гречка - «Из доброго в злое»","https://cdn1.intermedia.ru/img/news/344773.jpg","Может быть, Гречка и не переплюнет «Люби меня, люби» по популярности, но в аспекте качества она уже явно превзошла себя. Благодаря «Из доброго в злое» линия альбомов исполнительницы обзавелась стрелкой на конце и превратилась в вектор ее развития. Напомним, все началось с «Звезды только ночью», где мы услышали полтора хита, ужасный звук, примитивность и серость во всем. Далее был «Мы будто персонажи»: никакого резонанса, звук все еще достаточно топорный, но получше, идеи проблескивают сквозь туман бедности языка. И, наконец, «Из доброго в злое». Что мы услышим на этот раз?"),
//                   new NewsData("ПЕЛАГЕЯ В РОСТОВЕ","https://pics.utro.ru/utro_photos/2019/12/26/1430431.jpg","Всем смотреть на Пелагею"),
//                   new NewsData("14 марта пройдет Зонг опера в память умершего солиста группы 'Король и Шут' Михаила Горшнева","https://sun9-54.userapi.com/c855128/v855128486/1dd25e/7hb91ciRebM.jpg","Билеты можно купить по ссылке http://biletniystol.ru/rostov-na-donu/rock-musical-todd-bilety-14-03-2020"),
//                   new NewsData("Цой жив!","https://images11.popmeh.ru/upload/img_cache/e78/e7858c0817815534091922eb327a6b44_ce_794x794x243x0_cropped_800x800.jpg","И другие заявления фанатов"),
//                   new NewsData("Кровосток как всегда в своем репертуаре","https://cdn.kassir.ru/nn/poster/70/70378a7ad0d15c53080c3e4a23871855.jpg","Если запикать все маты, все равно нельзя крутить по радио"),
//                   new NewsData("Новости мира музыки","https://i.imgur.com/QsMxb2f.jpg","Как показывает статистика мало кого вообще  волнуют новости из мира музыки."),
//                   new NewsData("Летова нашли прячущимся в Тайге","https://ndn.info/images/dimensions/xl/ac8717f117172c129a27516cb01b2828_XL.jpg","Непонятно откуда у него кот, но надеюсь он его кормит"),
//                   new NewsData("Ниша новостей из сферы музыки скудна и однообразна, заявляют эксперты","https://bipbap.ru/wp-content/uploads/2019/10/Grustnye-15.jpg","И правда, очень мало интересных новостей"),
//                   new NewsData("Шокирующие новости! Чтобы увидеть настоящие новости...","https://pbs.twimg.com/profile_images/1189275922240811009/kjVm1yVY_400x400.jpg","Нужно всего лишь пролистать вниз"),
//                   new NewsData("Люди которые перешли с Windows на Linux","https://sun9-35.userapi.com/c837637/v837637260/5942d/QKzye39XIgI.jpg","Все как один отмечают, что их кожа стала мягче, лицо моложе, а играть музыку они стали виртуозно!"),
//                   new NewsData("Горшок ЖИВ!","https://funeralportal.ru/upload/iblock/bfc/403693.jpg","Да шутка повторилась но хуже от этого не стала."),
//                   new NewsData("Last.fm","https://www.iguides.ru/upload/medialibrary/c3b/c3b22e87226ce45d6100a9ab0f9942dc.png","Last.fm живет и процветает, осталось только добавить интеграцию в ЯндексМузыку тогда заживем!"),
//                   new NewsData("ЯндексМузыка за 99 рублей!","https://yt3.ggpht.com/a/AGF-l7_3L_I5maGpVKDugzwEPEak9al3ufizsrkUZw=s900-c-k-c0xffffffff-no-rj-mo","Акция невиданной щедрости, аж в два раза дешевле!"),
//                   new NewsData("K-POP Переоценен!","https://cdn.eksmo.ru/v2/ITD000000001043430/COVER/cover1__w600.jpg","Не слушал, но смею судить."),
//                   new NewsData("Что стало с Т.А.Т.У.?","https://n1s1.hsmedia.ru/d4/f0/36/d4f03680616433d136e26a755a1aaa88/2143x1295_0x0a330ca2_13653060181557641052.jpeg","Никто не знает, но каждый переодически переслушивает их песни."),
//                   new NewsData("Рецензия на клип: Вася Обломов - «Диги-диги»","https://cdn1.intermedia.ru/img/news/344756.jpg","Песня «Диги-диги» входила в недавний альбом Васи Обломова «Этот прекрасный мир» и была там одной из самых милых и мелодичных. Хотя «Диги-диги» и является сокращением от «дегенератов», в устах Васи это звучит почти нежно - что-то типа интонаций, с которой произносится фраза «они же дети». Клип на эту песню Обломов режиссировал сам и не стал экранизировать композицию близко к её тексту («они по левой полосе при свободных правых, у них парковка на газонах для самых-самых, они и делают ремонт, и дома строят, их услуги говно, они дорого стоят»). Вместо этого Вася Обломов, сосредоточился, на, так сказать, определённом виде дегенератов - тех, кто посещает тренинги личностного роста.")
           );
        }

    private void initRecyclerView() {
        RecyclerView newsRecyclerView = findViewById(R.id.newsRecyclerView);
        newsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        newsAdapter = new NewsAdapter();
        newsRecyclerView.setAdapter(newsAdapter);
    }


}
