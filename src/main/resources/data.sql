-- Insert sample data for users
INSERT INTO users (username, password) VALUES
    ('user1', 'password1'),
    ('user2', 'password2');

-- Insert sample data for roles
INSERT INTO roles (name) VALUES
    ('ROLE_ADMIN'),
    ('ROLE_USER');

-- Insert sample data for permissions
INSERT INTO permissions (name) VALUES
    ('READ'),
    ('WRITE');

-- Assign roles to users
INSERT INTO user_roles (user_id, role_id) VALUES
    (1, 1),
    (2, 2);

-- Assign permissions to roles
INSERT INTO role_permissions (role_id, permission_id) VALUES
    (1, 1),
    (2, 2);
