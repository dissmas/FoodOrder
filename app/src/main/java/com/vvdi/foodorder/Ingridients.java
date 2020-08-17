package com.vvdi.foodorder;

public class Ingridients
{
    //Список ингридиентов для пиццы
    private boolean _sause;         //соус
    private boolean _sausage;       //салями
    private boolean _pipperony;     //пиперони
    private boolean _ham;           //ветчина
    private boolean _chiken;        //куриное феле
    private boolean _onion;         //лук
    private boolean _pepper;        //перец
    private boolean _cheese;        //сыр
    private boolean _mozzarella;    //моцарелла
    private boolean _mushrooms;     //грибы
    private boolean _olive;         //оливки
    private boolean _greens;        //зелень


    public Ingridients()
    {

    }

    public void set_sause(boolean _sause) {
        this._sause = _sause;
    }

    public void set_sausage(boolean _sausage) {
        this._sausage = _sausage;
    }

    public void set_onion(boolean _onion) {
        this._onion = _onion;
    }

    public void set_pepper(boolean _pepper) {
        this._pepper = _pepper;
    }

    public void set_cheese(boolean _cheese) {
        this._cheese = _cheese;
    }

    public void set_mushrooms(boolean _mushrooms) {
        this._mushrooms = _mushrooms;
    }

    public void set_olive(boolean _olive) {
        this._olive = _olive;
    }

    public void set_pipperony(boolean _pipperony) { this._pipperony = _pipperony; }

    public void set_ham(boolean _ham) { this._ham = _ham; }

    public void set_chiken(boolean _chiken) { this._chiken = _chiken; }

    public void set_mozzarella(boolean _mozzarella) { this._mozzarella = _mozzarella; }

    public void set_greens(boolean _greens) { this._greens = _greens; }


    public boolean is_sause() {
        return _sause;
    }

    public boolean is_sausage() {
        return _sausage;
    }

    public boolean is_onion() {
        return _onion;
    }

    public boolean is_pepper() {
        return _pepper;
    }

    public boolean is_cheese() {
        return _cheese;
    }

    public boolean is_mushrooms() {
        return _mushrooms;
    }

    public boolean is_olive() {
        return _olive;
    }

    public boolean is_pipperony() {
        return _pipperony;
    }

    public boolean is_ham() {
        return _ham;
    }

    public boolean is_chiken() {
        return _chiken;
    }

    public boolean is_mozzarella() {
        return _mozzarella;
    }

    public boolean is_greens() {
        return _greens;
    }
}
