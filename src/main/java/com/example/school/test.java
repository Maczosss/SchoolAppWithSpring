//package com.example.school;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class test {
//
//    public static void main(String[] args) {
//
//        int rows = 4;
//        List<List<Container>> tab = new ArrayList<>();
//        tab.add(new ArrayList<>());
//        for (int i = 0; i < rows; i++) {
//            tab.get(0).add(new Container());
//            tab.get(0).get(i).setValue("*" + String.valueOf(i));
//            System.out.println(tab.get(0).toString());
//        }
//
//        System.out.println(tab);
//    }
//
//
//}
//
//class Container{
//
//    String value = "";
//    boolean isEdge = false;
//
//    public void setValue(String value){
//        this.value=value;
//    }
//
//    public String getValue(){return value;}
//
//    @Override
//    public String toString() {
//        return "Container{" +
//                "value='" + value + '\'' +
//                '}';
//    }
//}
