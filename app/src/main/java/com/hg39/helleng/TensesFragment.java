package com.hg39.helleng;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class TensesFragment extends Fragment {

    TextView ruleTextView;
    int testType;

    FirebaseDatabase database;
    DatabaseReference tests;

    String rule;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        testType = getArguments().getInt("testType");

        database = FirebaseDatabase.getInstance();
        tests = database.getReference("Tests");

        //До лучших времён
        /*tests.child("Tenses").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (testType == 4)
                {
                    rule = snapshot.child("Present Simple").child("Rules").child("rule1").getValue(String.class);
                }
                else if (testType == 5)
                {
                    rule = snapshot.child("Past Simple").child("Rules").child("rule1").getValue(String.class);
                }
                else if (testType == 6)
                {
                    rule = snapshot.child("Future Simple").child("Rules").child("rule1").getValue(String.class);
                }

                updateUI();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View rootView =
                inflater.inflate(R.layout.fragment_tenses,container,false);

        ruleTextView = rootView.findViewById(R.id.ruleTextView);

        switch (testType) {
            case 4:
                setRuleForPresentSimple();
                break;
            case 5:
                setRuleForPastSimple();
                break;
            case 6:
                setRuleForFutureSimple();
                break;
        }

        updateUI();

        return rootView;
    }

    private void updateUI() {
        ruleTextView.setText(rule);
    }

    private void setRuleForFutureSimple(){
                rule = "Present Simple - это простое настоящее время, обозначающее действие в самом широком смысле этого слова. Одна из самых распространенных и простых форм в английском языке для описания действий.\n" +
                        "\n" +
                        "Действия могут быть связаны с привычками, хобби, ежедневным событием вроде подъема по утрам или чем-то, что случается регулярно.\n" +
                        "\n" +
                        "Как образуется Present Simple?\n" +
                        "Нет ничего проще, чем поставить глагол в форму Present Simple. Для этого нужно убрать у глагола в инфинитиве частицу « to » и поставить глагол после подлежащего. Это и есть основное правило Present Simple.\n" +
                        "\n" +
                        "Утверждение:\n" +
                        "I / We / You / They + V\n" +
                        "\n" +
                        "She / He / It + V + s ( es )\n" +
                        "\n" +
                        "I go to work every day — Я хожу на работу каждый день.\n" +
                        "\n" +
                        "They usually play tennis every weekend — Они обычно играют в теннис каждые выходные.\n" +
                        "\n" +
                        "She brings me coffee every morning — Она приносит мне кофе каждое утро.\n" +
                        "\n" +
                        "It snows in winter — Зимой идет снег.\n" +
                        "\n" +
                        "ВАЖНО: В Present Simple форма глагола практически всегда совпадает с изначальной. Исключение составляет третье лицо единственного числа ( he / she / it ) — к нему прибавляется окончание - s :\n" +
                        "\n" +
                        "I ride — She rides\n" +
                        "\n" +
                        "I dream — He dreams\n" +
                        "\n" +
                        "Если глагол оканчивается на - s, - ss, - sh, - ch, - x, - o, то к нему прибавляется окончание - es\n" +
                        "\n" +
                        "I wish — She wishes\n" +
                        "\n" +
                        "I teach — She teaches\n" +
                        "\n" +
                        "Если глагол оканчивается на - y , а ему предшествует согласная, то к нему прибавляется окончание - es , но - y заменяется на - i\n" +
                        "\n" +
                        "I try — She tries\n" +
                        "\n" +
                        "I fly — He flies\n" +
                        "\n" +
                        "Если глагол оканчивается на - y , а ему предшествует гласная, то к нему также прибавляется окончание - s , но - y не меняется.\n" +
                        "\n" +
                        "I play — She plays\n" +
                        "\n" +
                        "I stay — He stays\n" +
                        "\n" +
                        "Отрицание:\n" +
                        "Чтобы составить отрицательное предложение — нужно поставить вспомогательный глагол между подлежащим и глаголом.\n" +
                        "\n" +
                        "I / We / You / They + do not (don’t) + V\n" +
                        "\n" +
                        "She / He / It + does not (doesn’t) + V\n" +
                        "\n" +
                        "I don’t go to school every day — Я не хожу в школу каждый день\n" +
                        "\n" +
                        "They don’t drink beer — Они не пьют пиво\n" +
                        "\n" +
                        "She doesn’t like the weather in London — Ей не нравится погода в Лондоне\n" +
                        "\n" +
                        "He doesn't drive the car — Он не водит машину\n" +
                        "\n" +
                        "Отрицание также можно выразить при помощи отрицательных местоимений и наречий.\n" +
                        "\n" +
                        "Nobody speaks Arabic — Никто не говорит по-арабски\n" +
                        "\n" +
                        "I do nothing — Я ничего не делаю\n" +
                        "\n" +
                        "Вопрос:\n" +
                        "При составлении вопросительных предложений вспомогательный глагол ставится перед подлежащим и последующим глаголом. Обычно — в начало предложения.\n" +
                        "\n" +
                        "Do + I / we / you / they + V?\n" +
                        "\n" +
                        "Does + she / he / it + V?\n" +
                        "\n" +
                        "Do you like pizza? — Тебе нравится пицца?\n" +
                        "\n" +
                        "Do they play football? — Они играют в футбол?\n" +
                        "\n" +
                        "Does she learn Russian? — Она изучает русский язык?\n" +
                        "\n" +
                        "Does he live in Spain? — Он живет в Испании?\n" +
                        "\n" +
                        "Иногда в вопросительном предложении употребляются question words (вопросительные слова) и фразы, которые помогают задать более точный и корректный вопрос собеседнику.\n" +
                        "\n" +
                        "К таким словам относятся: how long (как долго), why (почему), where (где) и другие. Как и в других временах, они ставятся в самом начале предложения перед вопросительной конструкцией Present Simple.\n" +
                        "\n" +
                        "QW + do ( does ) + I / We / You / They / She / He / It + V?\n" +
                        "\n" +
                        "Where does he live in Prague? — Где он живет в Праге?\n" +
                        "\n" +
                        "Why do you drink green tea? — Почему ты пьешь зеленый чай?\n" +
                        "\n" +
                        "Когда в предложении с Present Simple появляется вспомогательный глагол — у основного глагола пропадает окончание - s . Считайте, что это такой своеобразный «магнит», который «перетягивает» к себе это окончание. Это касается отрицательной и вопросительной форм Present Simple.\n" +
                        "\n" +
                        "ВАЖНО: иногда вспомогательный глагол do / does можно встретить и в утвердительных предложениях, чтобы добавить экспрессии и яркости высказыванию.\n" +
                        "\n" +
                        "I do want to try this — Я действительно хочу это попробовать\n" +
                        "\n" +
                        "Mary does know how to cook — Мэри действительно умеет готовить\n" +
                        "\n" +
                        "Глагол to be в Present Simple\n" +
                        "Глагол to be (быть) всегда является особенным и его употребление во времени Present Simple зависит от подлежащего. Он имеет 3 разных формы:\n" +
                        "\n" +
                        "am (для 1-го лица единственного числа: I )\n" +
                        "is (для 3-го лица единственного числа: she / he / it )\n" +
                        "are (для 1-го, 2-го и 3-го лица множественного числа: we / you / they )\n" +
                        "I am ready — Я готов\n" +
                        "\n" +
                        "She is ready — Она готова\n" +
                        "\n" +
                        "We are ready — Мы готовы\n" +
                        "\n" +
                        "Когда используется Present Simple?\n" +
                        "Present Simple используется в описании действий, которые происходят постоянно, на регулярной\n" +
                        "\n" +
                        "основе, но не привязаны к моменту речи.\n" +
                        "\n" +
                        "Употребление Present Simple уместно в тех случаях, когда мы хотим рассказать о нашей ежедневной рутине, достоверно известных фактах, действиях в широком смысле слова или расписании поездов.\n" +
                        "\n" +
                        "Регулярные, повторяющиеся действия:\n" +
                        "I often go to the bar — Я часто хожу в бар\n" +
                        "\n" +
                        "They play music every Sunday — Они играют музыку каждое воскресенье\n" +
                        "\n" +
                        "Действие в широком смысле слова (без привязки к моменту речи):\n" +
                        "I live in Dublin — Я живу в Дублине.\n" +
                        "\n" +
                        "She speaks Chinese — Она говорит по-китайски.\n" +
                        "\n" +
                        "Факты, о которых знают все:\n" +
                        "The Earth rotates around its axis — Земля вращается вокруг своей оси.\n" +
                        "\n" +
                        "Moscow is the largest city in Russia — Москва самый большой город в России\n" +
                        "\n" +
                        "Будущие действия, которые произойдут согласно расписанию:\n" +
                        "The airplane takes off at 4.30 am — Самолет взлетит в 4.30 утра.\n" +
                        "\n" +
                        "The train leaves at 9 pm tomorrow — Поезд отходит завтра в 9 вечера.\n" +
                        "\n" +
                        "Рецепты и инструкции (используется вместо повелительного наклонения):\n" +
                        "You push the red button to turn on the radio — Нажмите на красную кнопку, чтобы включить радио\n" +
                        "\n" +
                        "First you turn left and then you go down the street — Сперва поверните налево, затем идите до конца улицы\n" +
                        "\n" +
                        "При перечислении каких-то действий и их определенной последовательности также используется время Present Simple\n" +
                        "\n" +
                        "You take the bus into the city center and then you take a taxi to the restaurant — Вы едете на автобусе до центра города, а затем берете такси до ресторана.\n" +
                        "\n" +
                        "Иногда время Present Simple используется в отношении прошедшего времени. Например, в заголовках газет (указывают на факт произошедшего действия) или в рассказе о событии (когда мы говорим о ком-то и его действиях).\n" +
                        "\n" +
                        "The bus with American tourists crashes in India — В Индии разбился автобус с американскими туристами\n" +
                        "\n" +
                        "I met John last week. He comes to me and says : “ Hello, old friend ” — На прошлой неделе я встретил Джона. Он подошел ко мне и сказал: «Привет, старый друг»\n" +
                        "\n" +
                        "Маркеры времени Present Simple\n" +
                        "Для того, чтобы лучше сориентироваться где и когда употребляются глаголы Present Simple — обратите внимание на особые маркеры в тексте.\n" +
                        "Такими «маячками» для Present Simple являются наречия ( often, always, usually, etc. ) и указатели времени ( every day, in the morning, on Fridays, etc. ).\n" +
                        "\n" +
                        "She always drinks coffee in the morning — Она всегда пьет кофе по утрам\n" +
                        "\n" +
                        "I usually wake up at 6 am — Обычно я просыпаюсь в 6 утра\n" +
                        "\n" +
                        "They often talk about sport — Они часто говорят о спорте\n" +
                        "\n" +
                        "I check my smartphone every 15 minutes — Я проверяю свой телефон каждые 15 минут\n" +
                        "\n" +
                        "He takes a shower twice a day — Он принимает душ два раза в день\n" +
                        "\n" +
                        "On Mondays we go to the central park — По понедельникам мы ходим в центральный парк\n" +
                        "\n" +
                        "He comes here sometimes — Иногда он приходит сюда\n" +
                        "\n" +
                        "Примеры предложений с Present Simple :\n" +
                        "Утвердительные предложения:\n" +
                        "I read a book every evening — Я читаю книгу каждый вечер\n" +
                        "\n" +
                        "He likes to be polite — Ему нравится быть вежливым\n" +
                        "\n" +
                        "It takes two hours to fly from Berlin — Полет из Берлина займет два часа\n" +
                        "\n" +
                        "Cats like milk — Кошки любят молоко\n" +
                        "\n" +
                        "Отрицательные предложения:\n" +
                        "I don’t buy food in the supermarket — Я не покупаю еду в супермаркете\n" +
                        "\n" +
                        "He doesn’t play piano very well — Он не очень хорошо играет на пианино\n" +
                        "\n" +
                        "They don’t read books — Они не читают книги\n" +
                        "\n" +
                        "Duck don’t eat fish — Утки не едят рыбу\n" +
                        "\n" +
                        "Вопросительные предложения:\n" +
                        "Do you live in Paris? — Ты живешь в Париже?\n" +
                        "\n" +
                        "Does she play in a band? — Она играет в группе?\n" +
                        "\n" +
                        "Do you eat fish? — Ты ешь рыбу?\n" +
                        "\n" +
                        "Do they like coffee? — Им нравится кофе?";
    }

    private void setRuleForPastSimple() {
                rule = "Past Simple – простое прошедшее время. Оно используется, если событие произошло в определенное время в прошлом и не продолжается в настоящем. Часто в Past Simple используются такие слова, как ago( 5 days ago – 5 дней назад), last … (last year – в прошлом году), yesterday (вчера), in …( in 1980 – в 1980 году) и т.п.\n" +
                        "\n" +
                        "Past Simple указывает на простое действие в прошлом, регулярные или повторявшиеся действия в прошлом или перечисление последовавших действий в прошлом:\n" +
                        "\n" +
                        "Примеры:\n" +
                        "\n" +
                        "She went to the store this afternoon.\n" +
                        "They called the police.\n" +
                        "He came, he saw, he conquered.\n" +
                        "I lived in London for three years.\n" +
                        "She owned three dogs throughout her childhood.\n" +
                        "I never trusted what they told me.\n" +
                        "Как образуется Past Simple\n" +
                        "\n" +
                        "Чтобы образовать Past Simple, нужно употребить вторую форму глагола. У большинства глаголов она образуется путем добавления окончания “-ed” к инфинитиву. Если глагол заканчивается на “e“, добавляется просто “d“, если глагол заканчивается “y“, то она заменяется на “i” перед ):\n" +
                        "\n" +
                        "to walk –> walked\n" +
                        "to answer –> answered\n" +
                        "to want –> wanted\n" +
                        "to smile –> smiled\n" +
                        "to cry –> cried\n" +
                        "В то же время есть и неправильные глаголы, вторая форма которых образуется по-другому. Их нужно просто запомнить:\n" +
                        "\n" +
                        "to be –> was (singular), were (plural)\n" +
                        "to have –> had\n" +
                        "to do –> did\n" +
                        "to make –> made\n" +
                        "to eat –> ate\n" +
                        "to go –> went\n" +
                        "to drink –> drank\n" +
                        "to think –> thought\n" +
                        "to bring –> brought\n" +
                        "to drive –> drove\n" +
                        "to write –> wrote\n" +
                        "to sing –> sang\n" +
                        "to build –> built\n" +
                        "и т.д.\n" +
                        "\n" +
                        "Отрицательные и вопросительные предложения в Past Simple строятся с помощью добавления формы прошедшего времени глагола “to do – did” и инфинитива смыслового глагола:\n" +
                        "\n" +
                        "Did you arrive in time?\n" +
                        "Didn’t you eat yet?\n" +
                        "We didn’t go to the movies after all.\n" +
                        "Глагол “to be” в отрицательном или вопросительном предложении в Past Simple не требует добавления глагола “to do“:\n" +
                        "\n" +
                        "WasI late?\n" +
                        "Was he glad?\n" +
                        "Were you all right?";

    }

    private void setRuleForPresentSimple() {
                rule =
                "Употребление Present Simple Tense\n" +
                "\n" +
                "Простое настоящее время мы используем, в следующих случаях:\n" +
                "\n" +
                "1. Когда речь идет о чем-то в общем, а также об общеизвестных фактах\n" +
                "\n" +
                "Например:\n" +
                "\n" +
                "The Queen of England lives in Buckingham Palace.\n" +
                "Королева Англии живет в Букингемском Дворце.\n" +
                "Nurses look after patients in hospitals.\n" +
                "Медсестры ухаживают за больными в поликлиниках.\n" +
                "The earth goes around the sun.\n" +
                "Земля вертится вокруг Солнца.\n" +
                "A dog has four legs.\n" +
                "У собаки четыре лапы.\n" +
                "\n" +
                "2. Говоря о повторяющихся, обычных действиях в настоящем времени, происходящих вообще, а не в момент речи, а также для будущих событий (расписания)\n" +
                "\n" +
                "Например:\n" +
                "\n" +
                "The train to Prague leaves every hour.\n" +
                "Поезд в Прагу отправляется каждый час.\n" +
                "Jane sleeps eight hours every night during the week.\n" +
                "Джейн спит по восемь часов каждую ночь на протяжении недели.\n" +
                "The coffeehouse opens at 9.00 in the morning.\n" +
                "Кофейня открывается в 9.00 утра.\n" +
                "\n" +
                "3. Также для описания привычек\n" +
                "\n" +
                "I usually go away at the weekends.\n" +
                "Я обычно уезжаю куда-то на выходных.\n" +
                "Carol brushes her teeth twice a day.\n" +
                "Кэрол чистит зубы дважды в день.\n" +
                "They travel to their country house every weekend.\n" +
                "Они ездят на дачу каждые выходные.\n" +
                "\n" +
                "Таким образом, если вы хотите сказать, что-то происходит каждый день, постоянно, регулярно или это общеизвестный факт, используйте простое настоящее время." +
                "Как образовывается Present Simple Tense\n" +
                "Все просто: для образования утвердительного предложения нужно взять английский глагол без частицы to, либо же добавить к нему окончание –s/es для 3 лица единственного числа, т.е. он, она, оно – he, she, it; и поставить его после субъекта (того, кто исполняет действие).\n" +
                "\n" +
                "Смотрите пример:\n" +
                "\n" +
                "I drive to work every day. – Я еду на машине на работу каждый день.\n" +
                "\n" +
                "He drives to another city every day. – Каждый день он ездит в другой город.\n" +
                "\n" +
                "Видите, в первом предложении мы просто взяли глагол (инфинитив без частицы to), а во втором добавили к нему окончание –s, потому что субъект исполнения действия «he – он».\n" +
                "\n" +
                "Что касается отрицательных и вопросительных предложений, то здесь появляются глаголы-помощники или вспомогательные глаголы, как их часто называют (auxiliary verbs): do и does + not (если это отрицание).\n" +
                "\n" +
                "В вопросительных предложениях  мы ставим do или does в начало предложения. Does используется для he, she, it. Для всех остальных – do.\n" +
                "\n" +
                "Плюс пропадает окончание –s/es в 3 лице ед.числа.\n" +
                "\n" +
                "Например:\n" +
                "\n" +
                "Do they go to school at weekends? – Они ходят в школу по выходным?\n" +
                "\n" +
                "Do you need a dictionary? – Тебе нужен словарь?\n" +
                "\n" +
                "Does he like pizza? – Ему нравится пицца?\n" +
                "\n" +
                "Чтобы ответить на вопрос грамматически правильно, запомните: отвечаем так, как спрашивают.\n" +
                "\n" +
                "Если вопрос начинался со слова Do you…?, то и отвечайте «Yes, I do.» или «No, I don’t» (=do not). Если вопрос начинается с Does he/she/it…? – тоже отвечайте «Yes, he/she/it does.» или «No, he/she/it doesn’t.» (=does not)\n" +
                "\n" +
                "Но если вопрос начинается со слов who, when, where, why, which или how, короткий ответ типа «Да» или «Нет» уже не подойдет.\n" +
                "\n" +
                "Conjugations of verbs in Present Simple\n" +
                "\n" +
                "Для того чтобы сделать предложение отрицательным, необходимо добавить частицу not к вспомогательному глаголу.\n" +
                "\n" +
                "Порядок слов будет следующим: субъект/объект + do/does not + основной глагол + все остальное.\n" +
                "\n" +
                "Например:\n" +
                "\n" +
                "I don’t like this T-shirt. – Мне не нравится эта футболка.\n" +
                "\n" +
                "She doesn’t go with us to the cinema. – Она не идет с нами в кино.\n" +
                "\n" +
                "Примечание:\n" +
                "\n" +
                "don’t = do not\n" +
                "doesn’t = does not\n" +
                "\n" +
                "Чаще используются короткие формы (don’t, doesn’t).";

    }
}
