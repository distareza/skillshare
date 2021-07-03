package com.jpa.crud;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Persistence;
import javax.persistence.Table;

/**
 * Demonstrate how to execute insert entity Book and Author with Composite Key using IdClass Annotation
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
 *  4.	observe that the annotation @IdClass is required on a Class that uses it to identified a Composite Primary Key (From Normal Simple Class) 
 *  
 *  Demonstrate How to assign a Composite Key with different strategy :
 *  		> 1. Assign the Embedded Object (@Embeddable) as Composite Primary Key in Entity Object
 *  		> 2. Assign Simple Object as Composite Primary Key in Entity Object with annotation @EmbeddedId
 *  		> 3. Defined the Simple Object as Composite Primary Key in Entity Object as addition annotation @IdClass, however the composite column should be defined as well
 *  
 *  
 */
public class InsertExample08 {
	
	public static class SongKey implements Serializable {
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
		Hibernate: 
		    
		    create table hit_song (
		       artistName varchar(255) not null,
		        songTitle varchar(255) not null,
		        albumName varchar(255),
		        releasedYear integer,
		        primary key (artistName, songTitle)
		    ) engine=MyISAM
	 *
	 * primary key is a combination of two table
	 * uses annotation IdClass
	 *
	 */
	
	@Entity
	@Table(name = "hit_song")
	@IdClass(value = SongKey.class)
	public class Songs {
		
		@Id
		private String artistName;
		@Id
		private String songTitle;
		
		private String albumName;
		private Integer releasedYear;

		public Songs() {
		}
		
		public Songs(String songTitle, String artistName, String albumName, Integer releasedYear) {
			this.artistName = artistName;
			this.songTitle = songTitle;
			this.albumName = albumName;
			this.releasedYear = releasedYear;
		}

		public String getArtistName() {
			return artistName;
		}

		public void setArtistName(String artistName) {
			this.artistName = artistName;
		}

		public String getSongTitle() {
			return songTitle;
		}

		public void setSongTitle(String songTitle) {
			this.songTitle = songTitle;
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
			        hit_song
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
		SELECT * FROM hit_song;
		-------------------- ---------------------------- ---------------------------- ------------ 
		artistName           songTitle                    albumName                    releasedYear 
		-------------------- ---------------------------- ---------------------------- ------------ 
		The Weeknd           Save your Tears              After Hours                  2020         
		Bruno Mars           Just the Way You are         Doo-Wops & Hooligans         2010         
		Nirvana              Smell Like Teen Spirit       Nevermind                    1991        

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
		new InsertExample08().runMain();
	}
}
