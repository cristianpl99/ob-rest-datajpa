package service;

import com.example.obrestdatajpa.entities.Book;

public class BookPriceCalculator {
    public double calculatePrice(Book book){
        double price = book.getPrice();
            if (book.getPages() > 300){
                price +=5;
            }
                price += 2;
            return price;
    }
}
