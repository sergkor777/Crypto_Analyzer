package controllers;

import actions.Action;
import entity.Result;
import enums.Actions;

public class MainController {
    public Result doAction(String actionNane, String[] parameters) {
        // тут валидация аргумента и если он 0 то выход
        //encode source.txt encode.txt 7
        Action action = Actions.find(actionNane);
        return action.execute(parameters);
    }
}
