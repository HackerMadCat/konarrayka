package ru.spbstu.icc.kspt.configuration.manager;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import ru.spbstu.icc.kspt.configuration.model.Model;

public class ConfigurationManager {
    private Context context;

    public ConfigurationManager(Context context) {
        this.context = context;
    }

    public void writeToInternalMemory(Model model) {
        Gson gson = new GsonBuilder().create();
        String json = gson.toJson(model);
        try (OutputStreamWriter osw = new OutputStreamWriter(
                context.openFileOutput("config_" + model.getId() + ".txt",
                        Context.MODE_PRIVATE))) {
            osw.append(json);
        } catch (IOException e) {
            Log.v("ERROR", "Writing error", e);
        }
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

