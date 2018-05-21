package com.clouway.crm.core;

import org.apache.commons.lang.StringUtils;
import org.junit.Test;

import java.util.Arrays;
import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;

public class greetingKataTest {


    Greeter greeter = new Greeter();

    @Test
    public void greetJohn(){

    String name = "John";

    assertThat(greeter.greet(name), is("Hello, John."));


    }

    @Test
    public void greetAnn(){

        String name = "Ann";

        assertThat(greeter.greet(name), is("Hello, Ann."));

    }

    @Test
    public void greetStranger(){

        String name = null;

        assertThat(greeter.greet(name), is("Hello, my friend."));
        assertThat(greeter.greet(""), is("Hello, my friend."));

    }


    @Test
    public void shoutJohn(){

        String name = "JOHN";

        assertThat(greeter.greet(name), is("HELLO JOHN!"));

    }

    @Test
    public void greetTwoFriends(){

        String[] names = {"John", "Ann"};


        assertThat(greeter.greet(names), is("Hello, John and Ann."));

    }

    @Test
    public void greetMultipleFriend(){

        String[] names = {"John", "Ann", "Leonard", "Chip"};

        assertThat(greeter.greet(names), is("Hello, John, Ann, Leonard, and Chip."));

    }

    @Test
    public void greetMultipleShouting(){

        String[] names = {"JOHN", "ANN", "BOBBY"};

        assertThat(greeter.greet(names), is("HELLO JOHN, ANN, AND BOBBY!"));

    }

    @Test
    public void greetMultipleNormallyAndShoutMultiple(){

        String[] names = {"John", "Ann", "LEONARD", "ROBERT", "Chip"};

        assertThat(greeter.greet(names), is("Hello, John, Ann, and Chip. AND HELLO LEONARD AND ROBERT!"));

    }

    @Test
    public void greetMultipleWithOneString(){

        String[] names = {"John", "Shawn, Chip"};

        assertThat(greeter.greet(names), is("Hello, John, Shawn, and Chip."));

    }

    @Test
    public void escapeIntentionalCommas(){

        String[] names = {"John", "\"Charlie, Diane\""};

        assertThat(greeter.greet(names), is("Hello, John and Charlie, Diane."));

    }



    private class Greeter {


        public String greet(String[] names){

            names = splitNames(names);

            StringBuilder builder = new StringBuilder();

            String[] normalGreets = Arrays.stream(names)
                    .filter(n -> !n.toUpperCase().equals(n))
                    .toArray(String[]::new);

            writeGreet(normalGreets, builder, false);

            String[] shoutingGreets = Arrays.stream(names)
                    .filter(n -> n.toUpperCase().equals(n))
                    .toArray(String[]::new);

            writeGreet(shoutingGreets, builder, true);

            return builder.toString();

        }

        public String greet(String name) {

           if(StringUtils.isBlank(name)) name = "my friend";

           return greet(new String[] {name});

        }

        private void writeGreet(String[] names, StringBuilder builder, boolean shouting){

            for(int i = 0; i < names.length; i++){

                if(i == 0){
                    if(shouting && builder.length() > 0) builder.append(" AND HELLO ");
                    else if(shouting) builder.append("HELLO ");
                    else builder.append("Hello, ");
                }

                if(i < names.length - 1){
                    builder.append(names[i]);
                    if(names.length == 2) builder.append(" ");
                    else builder.append(", ");
                }
                else {

                    if(names.length > 1){
                        if(shouting) builder.append("AND ");
                        else builder.append("and ");
                    }

                    builder.append(names[i]);

                    if(shouting) builder.append("!");
                    else builder.append(".");
                }

            }

        }


        private String[] splitNames(String[] names){

            List<String> newNames = new ArrayList<>();

            Arrays.stream(names)
                    .forEach(s -> newNames
                            .addAll(s.contains("\"") ?
                                    Arrays.asList(s.replace("\"", "")) : Arrays.asList(s.split(", "))));

            return newNames.stream().toArray(String[]::new);


        }

    }
}
