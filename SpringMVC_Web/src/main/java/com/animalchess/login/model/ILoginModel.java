package com.animalchess.login.model;

public interface ILoginModel {

    /**
     * IDとパスワードから有効なユーザーか否かを返す
     *
     * @param id       IDだが想定はメールアドレス
     * @param password パスワード
     * @return true:有効なユーザー, false:無効なユーザー
     */
    public boolean isValidUser(String id, String password);
}