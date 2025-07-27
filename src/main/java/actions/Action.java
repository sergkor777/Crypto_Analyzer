package actions;

import entity.Result;

public interface Action {
    Result execute(String[] parameters);
}
