package ie.cit.patrick;

import java.util.GregorianCalendar;

public class Book {
	
	private int id;
	private String title, author, publisher, isbn;
	private GregorianCalendar publicationDate;
	private boolean available;
	
	
	public Book(String title, String author, String publisher,
			String isbn, GregorianCalendar publicationDate, boolean available) {
		super();
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.isbn = isbn;
		this.publicationDate = publicationDate;
		this.available = available;
	}
	
	public Book(int id, String title, String author, String publisher,
			String isbn, GregorianCalendar publicationDate, boolean available) {
		super();
		this.id = id;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.isbn = isbn;
		this.publicationDate = publicationDate;
		this.available = available;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublisher() {
		return publisher;
	}

	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public GregorianCalendar getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(GregorianCalendar publicationDate) {
		this.publicationDate = publicationDate;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	
	
	@Override
	public String toString() {
		return "Book [id=" + id + ", title=" + title + ", author=" + author
				+ ", publisher=" + publisher + ", isbn=" + isbn
				+ ", publicationDate=" + publicationDate + ", available="
				+ available + "]";
	}
	
	
	
	
	

}
