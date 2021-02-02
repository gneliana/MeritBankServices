INSERT INTO users (id, username, password, authority, active)
	values(0, 'user', 'password', 'accountholder', true);
	
INSERT INTO users (id, username, password, authority, active)
	values(0, 'admin', 'password', 'ADMIN', true);
	
	

INSERT INTO authorities (id, username, authority)
	values(0, 'user', ROLE_USER');
		
INSERT INTO authorities (id, username, authority)
	values(0, 'admin', ROLE_ADMIN');