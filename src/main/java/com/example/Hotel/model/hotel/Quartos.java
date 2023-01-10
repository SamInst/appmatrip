package com.example.Hotel.model.hotel;

import javax.persistence.*;

@Embeddable

public class Quartos {

   int numberRooms;

//   public amountPeople(){
//       ArrayList<Rooms> rooms = new ArrayList<>();
//       rooms.add();
//   }

    public Quartos(int numberRooms) {
        this.numberRooms = numberRooms;
    }

    public int getNumberRooms() {
        return numberRooms;
    }

    public void setNumberRooms(int numberRooms) {
        this.numberRooms = numberRooms;
    }
}
