package service;

import com.example.obrestdatajpa.entities.Book;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class BookPriceCalculatorTest {

    @Test
    void calculatePrice() {
        Book book = new Book(1L, "El señor de los Anillos", "JRR Tolkien", 500, 200.0, LocalDate.now(), false );
        BookPriceCalculator calculator = new BookPriceCalculator();
        double price = calculator.calculatePrice(book);

        assertEquals(price, 207.0);
        assertTrue(price > 0);
    }
}