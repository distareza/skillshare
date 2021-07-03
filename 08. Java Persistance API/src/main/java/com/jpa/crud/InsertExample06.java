package com.jpa.crud;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.Persistence;
import javax.persistence.Table;

/**
 * Demonstrate how to execute insert entity Book and Author with Composite Key
 * A composite primary key, also called a composite key, is a combination of two or more columns to form a primary key for a table.
 * 
 * 	1.	Observe how the table is drop and create back define in META-INF/persistence.xml 
 * 			<property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>
 * 		use drop-and-create only to implement testing environment
 * 
 *  2.	Observe how the entities Class is defining Table name with annotation @Table(name="")
 *
 *  3.	Observe How to assign a Composite Class using Annotation @Embeddable 
 *  	The Composite Key Object should defines or overrides its hashCode and equals method
 *  		
 *  4.	observe that the annotation @Id is also required to identified a Composite Primary Key 
 *    
 *  
 */
public class InsertExample06 {
	
	/**
	 * Demonstrate a Embedded Object as Composite Key to be assign in Entity
	 * require to override hashCode() and equals() 
	 * 
	 */
	@Embeddable
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
		    create table popular_song (
		       	artistName varchar(255) not null,
		        songTitle varchar(255) not null,
		        albumName varchar(255),
		        releasedYear integer,
		        primary key (artistName, songTitle)
		    )
	 *
	 * primary key is a combination of two column 
	 *
	 */	
	@Entity
	@Table(name = "popular_song")
	public class Songs {
		
		@Id
		private SongKey songKey;
		
		private String albumName;
		private Integer releasedYear;

		public Songs() {
		}

		public Songs( String songTitle, String artistName, String albumName, Integer releasedYear) {
			this.songKey = new SongKey(artistName, songTitle);
			this.albumName = songTitle;
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
				        popular_song
				        (albumName, releasedYear, artistName, songTitle) 
				    values
				        (?, ?, ?, ?)
			 * 
			 */
			Songs firstSong = new Songs( "Save your Tears", "The Weeknd", "After Hours", 2020);
			Songs secondSong = new Songs( "Just the Way You are", "Bruno Mars", "Doo-Wops & Hooligans", 2010);
			Songs thridSong = new Songs( "Smell Like Teen Spirit", "Nirvana", "Nevermind", 1991);

			entityManager.persist(firstSong);
			entityManager.persist(secondSong);
			entityManager.persist(thridSong);

			/**
			 * 
		SELECT * FROM popular_song
		----------------- ------------------------- --------------------------- ------------ 
		artistName        songTitle                 albumName                   releasedYear 
		----------------- ------------------------- --------------------------- ------------ 
		The Weeknd        Save your Tears           Save your Tears             2020         
		Bruno Mars        Just the Way You are      Just the Way You are        2010         
		Nirvana           Smell Like Teen Spirit    Smell Like Teen Spirit      1991         
			 * 
			 */
		} catch (Exception ex) {
			System.err.println("An error occurred: " + ex);
			ex.printStackTrace();
		} finally {

			entityManager.getTransaction().commit();

			entityManager.close();
			factory.close();
		}
	}

	public static void main(String[] args) { 
		new InsertExample06().runMain();
	}
}
