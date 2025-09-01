CREATE TYPE device_state AS ENUM ('AVAILABLE', 'IN_USE', 'INACTIVE');

CREATE TABLE device (
  id            BIGSERIAL PRIMARY KEY,
  name          VARCHAR(255) NOT NULL,
  brand         VARCHAR(255) NOT NULL,
  state         device_state NOT NULL DEFAULT 'AVAILABLE',
  created_at    TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT NOW()
);
