package com.jpa.relationmapping;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Persistence;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Demonstrate One To One Relationship with Join Table
 * Hibernate modeled the mapping using separate Table to join both of Entities
 * The possible representations to model a one-to-one mapping relationship are
 * 		1. Using a foreign key reference to associated entities
 * 		2. By having the entities share a primary key
 * 	-->	3. Using a separate mapping table to model entity associations
 * 
 * 	1.	Observe how the table is drop and create back define in META-INF/persistence.xml 
 * 			<property name="javax.persistence.schema-generation.database.action" value="drop-and-create"/>
 * 
 *  	Observe data is initialize through insert statement in preloaded-onlineshop-data.sql 
 *  		 <property name="javax.persistence.sql-load-script-source" value="META-INF/preload-query.sql"/>
 *  	insert statements are structure in one line per statement
 *  	only triggered if the schema generation action is set to drop and create
 * 
 *  2.	Observe how the entities classes are defining Table name with annotation @Table(name="")
 *  
 *  3.	Look how the GeneratedValue is define in entity class with different strategy assigning primary key generator
 *  		> AUTO 		: uses default sequence (hibrnate_sequence)
 *  		> IDENTITY  : uses auto increment of each table
 *  		> SEQUENCE  : uses definied sequence
 *  		> TABLE 	: uses definied table object as sequence generator
 *  
 *  4.	Defined how to defined custom Column definition with @Column Annotation
 *  		check how the column name can be altered or adjust,
 *  		Column Field Type, Column length
 *  		specify the Floating point precision (max digit) and scale ( n digit after decimal ) 
 *  
 *  5.	Demonstrate How to Date Time Field stored in Database
 *  		Normal / DEFAULT / Temporal (TemporalType.TIMESTAMP) --> Store both Date and Time Info
 *  		Temporal (TemporalType.DATE) --> Store Only Date without Time info
 *  		Temporal (TemporalType.TIME) --> Store Only Time without Date info
 *  
 *  6.	Notice Annotation @OneToOne and @JoinTable that declare at method/fields on Owning Entity Classes   
 *     
 *  
 *  Mapping : 
 *  	voter (id) -> elections (voter_id) 
 *  	ballot (id) -> election (ballot_id)
 *  
 *  Table representation
 *  	New table mapping "elactions" was consist of foreing key "voter_id" amd "ballot_id"
 *  	foreign key "voter_id" is a reference to primary key of "voter" table "id"
 *  	foreign key "ballot_id" is a reference to primary key of "ballot" table "id"
 *  
 *  JPA representation
 *  	Voter Entity is able to retrieve its Ballot Entity, with getBallot() method
 *  	Ballot Entity is able to retrieve its Voter Entity, with getVoter() method
 *  
 *  	Voter Entity declaring "OneToOne" mapping annotation with "mappedBy" attribute as a signals key for relationship on other side.
 *  		MappedBy signals hibernate that the key for the relationship is on the other side.
 *  		This means that although you link 2 tables together, only 1 of those tables has a foreign key constraint to the other one. 
 *  		MappedBy allows you to still link from the table not containing the constraint to the other table.
 *  	Ballot Entity declaring "OneToOne" mapping annotation with "JoinTable" annotation to mapped a "table mapping" for both Entity (its Entity and his relation Entity)
 *  
 *
 */
public class Mapping05OneToOneJoinTable {

	/**
	 * 
	================================================================== 
    create table voter (
       id integer not null auto_increment,
        birth_date date,
        birth_place varchar(255),
        name varchar(255),
        primary key (id)
    ) engine=MyISAM
    ==================================================================

	 * 
	 *
	 */
	@Entity(name="Voter")
	@Table(name="voter")
	public static class Voter implements Serializable {
		
		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		private String name;
		
		@Temporal(TemporalType.DATE)
		@Column(name = "birth_date")
		private Date birthDate;
		
		@Column(name = "birth_place")
		private String birthPlace;
		
		@OneToOne(mappedBy = "voter")
		private Ballot ballot;
		
		public Voter() {
		}

		public Voter(String name, Date birthDate, String birthPlace) {
			super();
			this.name = name;
			this.birthDate = birthDate;
			this.birthPlace = birthPlace;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Date getBirthDate() {
			return birthDate;
		}

		public void setBirthDate(Date birthDate) {
			this.birthDate = birthDate;
		}

		public String getBirthPlace() {
			return birthPlace;
		}

		public void setBirthPlace(String birthPlace) {
			this.birthPlace = birthPlace;
		}

		public Ballot getBallot() {
			return ballot;
		}

		public void setBallot(Ballot ballot) {
			this.ballot = ballot;
		}

		@Override
		public String toString() {
			return String.format(" { %d, %s, %s, %s } ", this.id, this.name, this.birthDate, this.birthPlace);
		}

	}
	
	/**
	 * 
	 *   
    create table ballot (
       id integer not null auto_increment,
        candidate varchar(255),
        party varchar(255),
        voting_date date,
        primary key (id)
    ) engine=MyISAM
	================================================================== 
    create table elections (
       ballot integer,
        voter_id integer not null,
        primary key (voter_id)
    ) engine=MyISAM
    ==================================================================
    alter table elections 
       add constraint FKajiijeg3v7gv0enq4dtx73mp4 
       foreign key (ballot_id) 
       references voter (id)
    ==================================================================
    alter table elections 
       add constraint FKh3ogc9py331965m23qipb83ps 
       foreign key (voter_id) 
       references ballot (id)       
    ==================================================================
	 *       
	 *
	 */
	@Entity(name = "Ballot")
	@Table(name = "ballot")
	public static class Ballot implements Serializable {

		private static final long serialVersionUID = 1L;
		
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Integer id;
		
		private String candidate;
		
		private String party;
		
		@Column(name = "voting_date")
		@Temporal(TemporalType.DATE)
		private Date votingDate;

		@OneToOne
		@JoinTable(	
				name = "elections", 
				joinColumns  = { @JoinColumn( name = "ballot_id", referencedColumnName = "id") },
				inverseJoinColumns= { @JoinColumn( name = "voter_id", referencedColumnName = "id") }
		)
		private Voter voter;
		
		public Ballot() {
		}
		
		public Ballot(Voter voter, String candidate, String party, Date votingDate) {
			this.candidate = candidate;
			this.party = party;
			this.votingDate = votingDate;
			this.voter = voter;
		}

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getCandidate() {
			return candidate;
		}

		public void setCandidate(String candidate) {
			this.candidate = candidate;
		}

		public String getParty() {
			return party;
		}

		public void setParty(String party) {
			this.party = party;
		}

		public Date getVotingDate() {
			return votingDate;
		}

		public void setVotingDate(Date votingDate) {
			this.votingDate = votingDate;
		}

		public Voter getVoter() {
			return voter;
		}

		public void setVoter(Voter voter) {
			this.voter = voter;
		}

		@Override
		public String toString() {
			return String.format(" { %d, %s, %s, %s } ", this.id, this.candidate, this.party, this.votingDate);
		}
		
	}
	
	private EntityManagerFactory factory;
	private EntityManager em;

	/**
	 * 
	==================================================================
    insert 
    into
        voter
        (birth_date, birth_place, name) 
    values
        (?, ?, ?)
 	==================================================================
    insert 
    into
        ballot
        (candidate, party, voting_date) 
    values
        (?, ?, ?)
	==================================================================
    into
        elections
        (candidate_id, voter_id) 
    values
        (?, ?)        
    ==================================================================
	 * 
	 */
	public void insertData() throws Exception {
		em.getTransaction().begin();
		
		Voter voterOne = new Voter("Black Jack", 	new SimpleDateFormat("dd-MM-yyyy").parse("04-03-1995"), "Aklahoma");
		Voter voterTwo = new Voter("Chad Badwin", 	new SimpleDateFormat("dd-MM-yyyy").parse("15-07-1996"), "Barkley");
		
		em.persist(voterOne);
		em.persist(voterTwo);
		
		Ballot ballotOne = new Ballot(voterOne, "Carol Crosby", "Liberal Democrat", new SimpleDateFormat("dd-MM-yyyy").parse("04-11-2020"));
		Ballot ballotTwo = new Ballot(voterTwo, "Stephanie Mills", "Green Party", new SimpleDateFormat("dd-MM-yyyy").parse("05-11-2020"));
		
		em.persist(ballotOne);
		em.persist(ballotTwo);
		
		em.getTransaction().commit();
	}
	
	/**
	 * 
	voterOne = em.find(Voter.class, 1)
	voterOne.getBallot()
 	==================================================================
    select
        mapping05o0_.id as id1_63_0_,
        mapping05o0_.birth_date as birth_da2_63_0_,
        mapping05o0_.birth_place as birth_pl3_63_0_,
        mapping05o0_.name as name4_63_0_,
        mapping05o0_1_.voter_id as voter_id0_24_0_,
        mapping05o1_.id as id1_3_1_,
        mapping05o1_.candidate as candidat2_3_1_,
        mapping05o1_.party as party3_3_1_,
        mapping05o1_.voting_date as voting_d4_3_1_,
        mapping05o1_1_.ballot_id as ballot_i1_24_1_,
        mapping05o2_.id as id1_63_2_,
        mapping05o2_.birth_date as birth_da2_63_2_,
        mapping05o2_.birth_place as birth_pl3_63_2_,
        mapping05o2_.name as name4_63_2_,
        mapping05o2_1_.voter_id as voter_id0_24_2_ 
    from
        voter mapping05o0_ 
    left outer join
        elections mapping05o0_1_ 
            on mapping05o0_.id=mapping05o0_1_.ballot_id 
    left outer join
        ballot mapping05o1_ 
            on mapping05o0_1_.voter_id=mapping05o1_.id 
    left outer join
        elections mapping05o1_1_ 
            on mapping05o1_.id=mapping05o1_1_.voter_id 
    left outer join
        voter mapping05o2_ 
            on mapping05o1_1_.ballot_id=mapping05o2_.id 
    left outer join
        elections mapping05o2_1_ 
            on mapping05o2_.id=mapping05o2_1_.ballot_id 
    where
        mapping05o0_.id=?
    ==================================================================     	
    em.createQuery("SELECT a FROM Voter a", Voter.class).getResultList();
    ==================================================================
    select
        mapping05o0_.id as id1_63_,
        mapping05o0_.birth_date as birth_da2_63_,
        mapping05o0_.birth_place as birth_pl3_63_,
        mapping05o0_.name as name4_63_,
        mapping05o0_1_.voter_id as voter_id0_24_ 
    from
        voter mapping05o0_ 
    left outer join
        elections mapping05o0_1_ 
            on mapping05o0_.id=mapping05o0_1_.candidate_id
	==================================================================
	em.createQuery("SELECT a FROM Ballot a", Ballot.class).getResultList();
	==================================================================
    select
        mapping05o0_.id as id1_3_,
        mapping05o0_.candidate as candidat2_3_,
        mapping05o0_.party as party3_3_,
        mapping05o0_.voting_date as voting_d4_3_,
        mapping05o0_1_.candidate_id as candidat1_24_ 
    from
        ballot mapping05o0_ 
    left outer join
        elections mapping05o0_1_ 
            on mapping05o0_.id=mapping05o0_1_.voter_id            
    ==================================================================
	 * 
	 */
	public void retrieveData() {
		System.out.println("retrieve data");
		
//		Voter voterOne = em.find(Voter.class, 1);
//		System.out.println(voterOne);					//{ 1, John Wick, 1964-09-02, 	Jardani Jovonovich } 
//		System.out.println(voterOne.getBallot());		//{ 10, Denis Graham, UK Independence Party, 2021-05-07 } 
//		
//		Voter voterTwo = em.find(Voter.class, 3);		
//		System.out.println(voterTwo);					// { 3, Linda P. Johnson, 1951-10-21, Libya } 
//		System.out.println(voterTwo.getBallot());		// { 12, Jim Nunn, British National Party, 2019-04-02 } 


		Ballot ballotOne = em.find(Ballot.class, 10);
		System.out.println(ballotOne);					// { 10, Denis Graham, UK Independence Party, 2021-05-07 } 
		System.out.println(ballotOne.getVoter());		// { 1, John Wick, 1964-09-02, 	Jardani Jovonovich } 
		
		Ballot ballotTwo = em.find(Ballot.class, 12);
		System.out.println(ballotTwo);					// { 12, Jim Nunn, British { 3, Linda P. Johnson, 1951-10-21, Libya }National Party, 2019-04-02 } 
		System.out.println(ballotTwo.getVoter());		// 
		
		
		List<Voter> voters = em.createQuery("SELECT a FROM Voter a", Voter.class).getResultList();
		System.out.println(voters);
		
		List<Ballot> ballots = em.createQuery("SELECT a FROM Ballot a", Ballot.class).getResultList();
		System.out.println(ballots);

	}
	
	public void runMain() {
		factory = Persistence.createEntityManagerFactory("OnlineShoopingDB_Unit");
		em = factory.createEntityManager();

		try {
			
			insertData();
			retrieveData();

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			em.close();
			factory.close();
		}
	}
	
	
	public static void main(String[] args) {
		new Mapping05OneToOneJoinTable().runMain();
	}

}
