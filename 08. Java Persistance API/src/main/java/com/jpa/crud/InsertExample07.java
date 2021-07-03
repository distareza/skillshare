package com.jpa.crud;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Table;

/**
 * Demonstrate how to execute insert entity Book and Author with Composite Key using EmbededId Annotation
 * A composite primary key, also called a composite key, is a combination of two or more columns to form a primary key for a table.
 * 
 * 	1.	Observe how the table is drop and create back define in META-INF/persistence.xml 
 * 			<property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>
 * 		use drop-and-create only to implement testing environment
 * 
 *  2.	Observe how the entities Class is defining Table name with annotation @Table(name="")
 *
 *  3.	Observe How to assign a Composite Class using Simple Class (without using Annotation @Embeddable)
 *  	However The Composite Key Class still should defines or overrides its hashCode and equals method
 *  		
 *  4.	observe that the annotation @EmbeddedId is required on a Fields to identified a Composite Primary Key (From Normal Simple Class) 
 *  
 *  Demonstrate How to assign a Composite Key with different strategy :
 *  	Composite Key Object should defined or overrides its hashCode and equals method
 *  		> 1. Assign the Embedded Object (@Embeddable) as Composite Primary Key in Entity Object
 *  		> 2. Assign Simple Object as Composite Primary Key in Entity Object with annotation @EmbeddedId
 *  
 *  
 */
public class InsertExample07 {
	
	public class SongKey implements Serializable {
		private static final long serialVersionUID = 1L;
		
		private String artistName;
		private String songTitle;

		public SongKey() {
		}

		public SongKey(String artistName, String songTitle) {
			this.artistName = artistName;
			this.songTitle = songTitle;
		}

		@Override
		public int hashCode() {
			return Objects.hash(artistName, songTitle);
		}

		@Override
		public boolean equals(Object obj) {
			if (obj == null) return false;
			if (!(obj instanceof SongKey)) return false;
			SongKey other = (SongKey) obj;
			return artistName.equals(other.artistName) && songTitle.equals(other.songTitle);
		}
		
	}

	/**
	 * demonstrate how to embeded Composite Key into Entity as its primary key / identifier
	 * 
	 * Spring JPA will run following query 
		Hibernate: 
		    
		    create table classical_song (
		       artistName varchar(255) not null,
		        songTitle varchar(255) not null,
		        albumName varchar(255),
		        releasedYear integer,
		        primary key (artistName, songTitle)
		    ) engine=MyISAM
	 *
	 * primary key is a combination of two column, it reference with BookKey Field with Annotation @EmmbededId
	 * 
	 * 
	 */	
	@Entity
	@Table(name = "classical_song")
	public class Song {
		
		@EmbeddedId
		private SongKey songKey;
		
		private String albumName;
		private Integer releasedYear;

		public Song() {
		}
		
		public Song(String songTitle, String artistName, String albumName, Integer releasedYear) {
			this.songKey = new SongKey(artistName, songTitle);
			this.albumName = albumName;
			this.releasedYear = releasedYear;
		}
		public SongKey getSongKey() {
			return songKey;
		}

		public void setSongKey(SongKey songKey) {
			this.songKey = songKey;
		}

		public String getAlbumName() {
			return albumName;
		}

		public void setAlbumName(String albumName) {
			this.albumName = albumName;
		}

		public Integer getReleasedYear() {
			return releasedYear;
		}

		public void setReleasedYear(Integer releasedYear) {
			this.releasedYear = releasedYear;
		}

	}
	
	public void runMain() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("BookstoreDB_Unit");
		EntityManager entityManager = factory.createEntityManager();

		try {
			entityManager.getTransaction().begin();

			/**
			 * 
				Hibernate: 
				    insert 
				    into
				        classical_song
				        (albumName, releasedYear, artistName, songTitle) 
				    values
				        (?, ?, ?, ?)
			 * 
			 */
			Song firstSong = new Song( "Toccata and Fugue in D minor", "J.S. Bach", "BWV 565", 1704);
			Song secondSong = new Song("Für Elise", "Ludwig Van Beethoven", "Moonlight", 1827);
			Song thridSong = new Song("CPiano Sonata", "Ludwig Van Beethoven", "Moonlight", 1827);

			entityManager.persist(firstSong);
			entityManager.persist(secondSong);
			entityManager.persist(thridSong);

			/**
			 * 
			 *
	SELECT * FROM classical_song
	---------------------------- ------------------------------------ ---------------------- ------------ 
	artistName                   songTitle                            albumName              releasedYear 
	---------------------------- ------------------------------------ ---------------------- ------------ 
	J.S. Bach                    Toccata and Fugue in D minor         BWV 565                1704         
	Ludwig Van Beethoven         Für Elise                            Moonlight              1827         
	Ludwig Van Beethoven         CPiano Sonata                        Moonlight              1827         

			 * 
			 */
		} catch (Exception ex) {
			System.err.println("An error occurred: " + ex);
		} finally {

			entityManager.getTransaction().commit();

			entityManager.close();
			factory.close();
		}
	}

	public static void main(String[] args) { 
		new InsertExample07().runMain();
	}
}
