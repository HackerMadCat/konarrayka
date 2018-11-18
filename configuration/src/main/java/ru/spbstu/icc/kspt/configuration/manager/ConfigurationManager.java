package ru.spbstu.icc.kspt.configuration.manager;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import ru.spbstu.icc.kspt.configuration.model.Action;
import ru.spbstu.icc.kspt.configuration.model.ConditionalAction;
import ru.spbstu.icc.kspt.configuration.model.Hero;
import ru.spbstu.icc.kspt.configuration.model.Model;
import ru.spbstu.icc.kspt.configuration.model.Setup;

public class ConfigurationManager {
    private Context context;

    public ConfigurationManager(Context context) {
        this.context = context;
    }

    public void writeToInternalMemory(Model model) {
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(model);

        Set<File> sounds = populateSounds(model);
        Set<File> icons = populateIcons(model);

        try (OutputStreamWriter osw = new OutputStreamWriter(
                context.openFileOutput("config_" + model.getHeader().getId() + ".txt",
                        Context.MODE_PRIVATE))) {
            osw.append(json);
        } catch (IOException e) {
            Log.v("ERROR", "Writing error", e);
        }
    }

    private Set<File> populateSounds(Model model) {
        Set<File> sounds = new HashSet<>(model.getSounds());

        if (model.getRules() != null) {
            Set<Action> actions = model.getRules().getActions();
            for (Action action : actions) {
                sounds.addAll(action.getVoices());
            }

            List<ConditionalAction> conditionalActions = model.getRules().getConditionalActions();
            for (ConditionalAction ca : conditionalActions) {
                if (ca.getAction() != null) {
                    sounds.addAll(ca.getAction().getVoices());
                }
            }
        }
        return sounds;
    }

    private Set<File> populateIcons(Model model) {
        Set<File> icons = new HashSet<>();
        if (model.getHeader() != null) {
            icons.add(model.getHeader().getIcon());
        }

        if (model.getRules() != null) {
            for (Hero hero : model.getRules().getHeroes()) {
                icons.add(hero.getIcon());
            }

            for (Action action : model.getRules().getActions()) {
                icons.add(action.getIcon());
            }

            for (ConditionalAction ca : model.getRules().getConditionalActions()) {
                if (ca.getAction() != null) {
                    icons.add(ca.getAction().getIcon());
                }
                if (ca.getCondition() != null) {
                    for (Set<Hero> setHeroes : ca.getCondition().getDisjunctions()) {
                        for (Hero hero : setHeroes) {
                            icons.add(hero.getIcon());
                        }
                    }
                }
            }
        }

        for (Setup setup : model.getSetups()) {
            for (Hero hero : setup.getHeroes()) {
                icons.add(hero.getIcon());
            }
        }

        return icons;
    }


    public Model getModelById(int uuid) {
        try (InputStream is = context.openFileInput("config_" + uuid + ".txt")) {
            if (is != null) {
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String curStr;
                StringBuilder stringBuilder = new StringBuilder();
                while ((curStr = br.readLine()) != null) {
                    stringBuilder.append(curStr);
                }
                Gson gson = new GsonBuilder().create();
                String modelJson = stringBuilder.toString();
                return gson.fromJson(modelJson, Model.class);
            }
        } catch (IOException e) {
            Log.v("ERROR", "No file found :(", e);
        }
        return null;
    }
}

