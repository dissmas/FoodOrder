package com.vvdi.foodorder;

import java.util.ArrayList;

public class Pizza
{
    private String _id;
    private int _diametr;
    private int _count;
    private ArrayList<Ingridients> _ingredients;
    private Ingridients ingridients;


    public void setIngridients(Ingridients ingridients) {
        this.ingridients = ingridients;
    }
    public Ingridients getIngridients() {
        return ingridients;
    }

    public void set_ingredients(ArrayList<Ingridients> _ingridients) { this._ingredients = _ingridients; }
    public ArrayList<Ingridients> get_ingredients() {
        return _ingredients;
    }


    //Конструктор
    public Pizza()
    {

    }

    public String get_id() {
        return _id;
    }
    public int get_diametr() {
        return _diametr;
    }
    public int get_count() {
        return _count;
    }

    public void set_id( String _id) { this._id = _id; }
    public void set_diametr(int _diametr) {
        this._diametr = _diametr;
    }
    public void set_count(int _count) {
        this._count = _count;
    }
}
