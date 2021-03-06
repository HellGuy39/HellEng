package com.hg39.helleng;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TensesFragment extends Fragment {

    private TextView ruleTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //return super.onCreateView(inflater, container, savedInstanceState);
        View rootView =
                inflater.inflate(R.layout.fragment_tenses,container,false);

        ruleTextView = rootView.findViewById(R.id.ruleTextView);

        int testType = getArguments().getInt("testType");

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
        return rootView;
    }


    private void setRuleForFutureSimple(){

    }

    private void setRuleForPastSimple() {

    }

    private void setRuleForPresentSimple() {
                String rule =
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
                ruleTextView.setText(rule);
    }
}
