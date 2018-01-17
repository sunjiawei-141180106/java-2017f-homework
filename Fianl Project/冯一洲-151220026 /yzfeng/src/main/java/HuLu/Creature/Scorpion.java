package HuLu.Creature;

import HuLu.Field.Field;

import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.concurrent.CyclicBarrier;

public class Scorpion extends BadMan {
    public Scorpion(int id , Field field, CyclicBarrier cyclic){
        super(id, field, cyclic);
        URL loc = this.getClass().getClassLoader().getResource( "scorpion.png");
        ImageIcon iia = new ImageIcon(loc);
        Image image = iia.getImage();

        //load creature-die pic
        URL loc2 = this.getClass().getClassLoader().getResource("scorpiondead.png");
        ImageIcon iia2 = new ImageIcon(loc2);
        Image image2 = iia2.getImage();

        Image[] images = new Image[2];
        images[0] = image;
        images[1] = image2;


        this.setImage(images);
    }
}
