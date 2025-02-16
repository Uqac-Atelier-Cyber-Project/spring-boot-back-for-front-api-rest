-- TODO : modifier les valeurs des varchars (default 50) 

#------------------------------------------------------------
# Table: User
#------------------------------------------------------------

CREATE TABLE User(
        id_user         Varchar (50) NOT NULL,
        Username        Varchar (50) NOT NULL,
        hash_passwd_b64 Varchar (50) NOT NULL,
        CONSTRAINT User_PK PRIMARY KEY (id_user)
) ENGINE=InnoDB;

#------------------------------------------------------------
# Table: historique_connexion
#-----------------------------------------------------------

CREATE TABLE historique_connexion(
        id_connexion Int AUTO_INCREMENT NOT NULL,
        id_user      Varchar (50) NOT NULL,
        timestamp    TimeStamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
        location     Varchar (50) NOT NULL,
        plateforme   Varchar (50) NOT NULL,
        CONSTRAINT historique_connexion_PK PRIMARY KEY (id_connexion),
        CONSTRAINT historique_connexion_User_FK FOREIGN KEY (id_user) REFERENCES User(id_user) ON DELETE CASCADE
) ENGINE=InnoDB;

#------------------------------------------------------------
# Table: rapport
#------------------------------------------------------------

CREATE TABLE rapport(
        id_analyse         Int NOT NULL AUTO_INCREMENT,
        id_user            Varchar (50) NOT NULL,
        name               Varchar (50),
        encrypted_file_aes Varchar (50),
        isreaded           Bool NOT NULL DEFAULT FALSE,
        date_declanchement TimeStamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
        CONSTRAINT rapport_PK PRIMARY KEY (id_analyse),
        CONSTRAINT rapport_User_FK FOREIGN KEY (id_user) REFERENCES User(id_user) ON DELETE CASCADE
) ENGINE=InnoDB;

#------------------------------------------------------------
# Table: pending_analyse
#------------------------------------------------------------

CREATE TABLE pending_analyse(
        id_analyse Int NOT NULL,
        id_user    Varchar (50) NOT NULL,
        s1         Bool NOT NULL,
        s2         Bool NOT NULL,
        s3         Bool NOT NULL,
        s4         Bool NOT NULL,
        CONSTRAINT pending_analyse_rapport_FK FOREIGN KEY (id_analyse) REFERENCES rapport(id_analyse) ON DELETE CASCADE,
        CONSTRAINT pending_analyse_PK PRIMARY KEY (id_analyse),
        CONSTRAINT pending_analyse_User_FK FOREIGN KEY (id_user) REFERENCES User(id_user) ON DELETE CASCADE
) ENGINE=InnoDB;


#------------------------------------------------------------
# Table: results
#------------------------------------------------------------

CREATE TABLE results(
        id_analyse Int NOT NULL,
        s1         Varchar (50) NOT NULL,
        s2         Varchar (50) NOT NULL,
        s3         Varchar (50) NOT NULL,
        s4         Varchar (50) NOT NULL,
        CONSTRAINT results_rapport_FK FOREIGN KEY (id_analyse) REFERENCES rapport(id_analyse) ON DELETE CASCADE,
        CONSTRAINT results_PK PRIMARY KEY (id_analyse)
) ENGINE=InnoDB;
