CREATE TABLE sessions
(
  id SERIAL PRIMARY KEY,
  session_id VARCHAR(256) NOT NULL,
  created_on TIMESTAMP NOT NULL,
  expires_on TIMESTAMP NOT NULL,
  data TEXT,

  UNIQUE (session_id)
);
