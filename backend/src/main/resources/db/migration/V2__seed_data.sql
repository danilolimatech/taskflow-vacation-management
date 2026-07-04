INSERT INTO users (id, username, password, role, created_at, updated_at) VALUES
('33d1c2ad-7ced-4871-834c-f75a796306b6', 'admin.carlos',  '$2a$10$0/5rdnmjxqOqq335bQkiy.jVLu0sxLUAVl3NVdLh6r8FsotMnuw92', 'ADMIN', NOW(), NOW()),
('93e053ed-9b6a-4eca-8301-bae13508ecac', 'admin.ana',     '$2a$10$0/5rdnmjxqOqq335bQkiy.jVLu0sxLUAVl3NVdLh6r8FsotMnuw92', 'ADMIN', NOW(), NOW());

INSERT INTO users (id, username, password, role, created_at, updated_at) VALUES
('02290195-33d9-4011-b2a4-e54b1bf73e40', 'manager.joao',  '$2a$10$0/5rdnmjxqOqq335bQkiy.jVLu0sxLUAVl3NVdLh6r8FsotMnuw92', 'MANAGER', NOW(), NOW()),
('10996db5-d638-447f-9dbc-ac1a63595df0', 'manager.lucia', '$2a$10$0/5rdnmjxqOqq335bQkiy.jVLu0sxLUAVl3NVdLh6r8FsotMnuw92', 'MANAGER', NOW(), NOW()),
('2284c16f-36cd-4d3d-a10c-0e85af0ca580', 'manager.pedro', '$2a$10$0/5rdnmjxqOqq335bQkiy.jVLu0sxLUAVl3NVdLh6r8FsotMnuw92', 'MANAGER', NOW(), NOW()),
 ('f774634b-5db7-474d-908b-c5ad6706808a', 'manager.sofia', '$2a$10$0/5rdnmjxqOqq335bQkiy.jVLu0sxLUAVl3NVdLh6r8FsotMnuw92', 'MANAGER', NOW(), NOW());

INSERT INTO users (id, username, password, role, created_at, updated_at) VALUES
('36d0b929-d939-4ed9-a884-1e5aeae06e80', 'colab.marcos',   '$2a$10$0/5rdnmjxqOqq335bQkiy.jVLu0sxLUAVl3NVdLh6r8FsotMnuw92', 'COLLABORATOR', NOW(), NOW()),
('ba741a13-8be2-4167-81cd-9d32667fb45c', 'colab.beatriz',  '$2a$10$0/5rdnmjxqOqq335bQkiy.jVLu0sxLUAVl3NVdLh6r8FsotMnuw92', 'COLLABORATOR', NOW(), NOW()),
('d5ac7441-a3b9-44a7-8ebc-1326426c68bd', 'colab.rafael',   '$2a$10$0/5rdnmjxqOqq335bQkiy.jVLu0sxLUAVl3NVdLh6r8FsotMnuw92', 'COLLABORATOR', NOW(), NOW()),
('b54ad0ed-a9fe-4cf8-879d-dfbb70a3b067', 'colab.fernanda', '$2a$10$0/5rdnmjxqOqq335bQkiy.jVLu0sxLUAVl3NVdLh6r8FsotMnuw92', 'COLLABORATOR', NOW(), NOW()),
('4410e9a5-105b-4346-83bd-af97723f6782', 'colab.thiago',   '$2a$10$0/5rdnmjxqOqq335bQkiy.jVLu0sxLUAVl3NVdLh6r8FsotMnuw92', 'COLLABORATOR', NOW(), NOW()),
('f85b39f4-5b25-4df1-91cf-95e4c4fc4c94', 'colab.camila',   '$2a$10$0/5rdnmjxqOqq335bQkiy.jVLu0sxLUAVl3NVdLh6r8FsotMnuw92', 'COLLABORATOR', NOW(), NOW()),
('2e9a07cc-43d7-446a-bbb9-f0aafd2a84fd', 'colab.gabriel',  '$2a$10$0/5rdnmjxqOqq335bQkiy.jVLu0sxLUAVl3NVdLh6r8FsotMnuw92', 'COLLABORATOR', NOW(), NOW()),
('0987f393-8e1a-40ff-bbdd-88fc142b15c8', 'colab.juliana',  '$2a$10$0/5rdnmjxqOqq335bQkiy.jVLu0sxLUAVl3NVdLh6r8FsotMnuw92', 'COLLABORATOR', NOW(), NOW()),
('df351582-9638-40ca-a3a8-3a97b237e7c5', 'colab.rodrigo',  '$2a$10$0/5rdnmjxqOqq335bQkiy.jVLu0sxLUAVl3NVdLh6r8FsotMnuw92', 'COLLABORATOR', NOW(), NOW()),
('b008c55f-7237-40dd-8ed1-c68dd9373456', 'colab.patricia', '$2a$10$0/5rdnmjxqOqq335bQkiy.jVLu0sxLUAVl3NVdLh6r8FsotMnuw92', 'COLLABORATOR', NOW(), NOW()),
('75c5d8b8-a15f-4897-82b4-d6b40dd13227', 'colab.lucas',    '$2a$10$0/5rdnmjxqOqq335bQkiy.jVLu0sxLUAVl3NVdLh6r8FsotMnuw92', 'COLLABORATOR', NOW(), NOW()),
('6c5bd507-26fd-4688-b105-e10eb07d4b67', 'colab.amanda',   '$2a$10$0/5rdnmjxqOqq335bQkiy.jVLu0sxLUAVl3NVdLh6r8FsotMnuw92', 'COLLABORATOR', NOW(), NOW());

INSERT INTO employees (id, full_name, email, role, manager_id, user_id, created_at, updated_at) VALUES
('33d1c2ad-7ced-4871-834c-f75a796306b6', 'Carlos Mendes',  'carlos.mendes@taskflow.com',  'ADMIN', NULL, '33d1c2ad-7ced-4871-834c-f75a796306b6', NOW(), NOW()),
('93e053ed-9b6a-4eca-8301-bae13508ecac', 'Ana Oliveira',   'ana.oliveira@taskflow.com',   'ADMIN', NULL, '93e053ed-9b6a-4eca-8301-bae13508ecac', NOW(), NOW());

INSERT INTO employees (id, full_name, email, role, manager_id, user_id, created_at, updated_at) VALUES
('02290195-33d9-4011-b2a4-e54b1bf73e40', 'João Silva',     'joao.silva@taskflow.com',     'MANAGER', NULL, '02290195-33d9-4011-b2a4-e54b1bf73e40', NOW(), NOW()),
('10996db5-d638-447f-9dbc-ac1a63595df0', 'Lúcia Ferreira', 'lucia.ferreira@taskflow.com', 'MANAGER', NULL, '10996db5-d638-447f-9dbc-ac1a63595df0', NOW(), NOW()),
('2284c16f-36cd-4d3d-a10c-0e85af0ca580', 'Pedro Costa',    'pedro.costa@taskflow.com',    'MANAGER', NULL, '2284c16f-36cd-4d3d-a10c-0e85af0ca580', NOW(), NOW()),
('f774634b-5db7-474d-908b-c5ad6706808a', 'Sofia Martins',  'sofia.martins@taskflow.com',  'MANAGER', NULL, 'f774634b-5db7-474d-908b-c5ad6706808a', NOW(), NOW());

INSERT INTO employees (id, full_name, email, role, manager_id, user_id, created_at, updated_at) VALUES
('36d0b929-d939-4ed9-a884-1e5aeae06e80', 'Marcos Souza',   'marcos.souza@taskflow.com',   'COLLABORATOR', '02290195-33d9-4011-b2a4-e54b1bf73e40', '36d0b929-d939-4ed9-a884-1e5aeae06e80', NOW(), NOW()),
('ba741a13-8be2-4167-81cd-9d32667fb45c', 'Beatriz Lima',   'beatriz.lima@taskflow.com',   'COLLABORATOR', '02290195-33d9-4011-b2a4-e54b1bf73e40', 'ba741a13-8be2-4167-81cd-9d32667fb45c', NOW(), NOW()),
('d5ac7441-a3b9-44a7-8ebc-1326426c68bd', 'Rafael Alves',   'rafael.alves@taskflow.com',   'COLLABORATOR', '02290195-33d9-4011-b2a4-e54b1bf73e40', 'd5ac7441-a3b9-44a7-8ebc-1326426c68bd', NOW(), NOW());

INSERT INTO employees (id, full_name, email, role, manager_id, user_id, created_at, updated_at) VALUES
('b54ad0ed-a9fe-4cf8-879d-dfbb70a3b067', 'Fernanda Rocha', 'fernanda.rocha@taskflow.com', 'COLLABORATOR', '10996db5-d638-447f-9dbc-ac1a63595df0', 'b54ad0ed-a9fe-4cf8-879d-dfbb70a3b067', NOW(), NOW()),
('4410e9a5-105b-4346-83bd-af97723f6782', 'Thiago Nunes',   'thiago.nunes@taskflow.com',   'COLLABORATOR', '10996db5-d638-447f-9dbc-ac1a63595df0', '4410e9a5-105b-4346-83bd-af97723f6782', NOW(), NOW()),
('f85b39f4-5b25-4df1-91cf-95e4c4fc4c94', 'Camila Pereira', 'camila.pereira@taskflow.com', 'COLLABORATOR', '10996db5-d638-447f-9dbc-ac1a63595df0', 'f85b39f4-5b25-4df1-91cf-95e4c4fc4c94', NOW(), NOW());

INSERT INTO employees (id, full_name, email, role, manager_id, user_id, created_at, updated_at) VALUES
('2e9a07cc-43d7-446a-bbb9-f0aafd2a84fd', 'Gabriel Santos', 'gabriel.santos@taskflow.com', 'COLLABORATOR', '2284c16f-36cd-4d3d-a10c-0e85af0ca580', '2e9a07cc-43d7-446a-bbb9-f0aafd2a84fd', NOW(), NOW()),
('0987f393-8e1a-40ff-bbdd-88fc142b15c8', 'Juliana Castro', 'juliana.castro@taskflow.com', 'COLLABORATOR', '2284c16f-36cd-4d3d-a10c-0e85af0ca580', '0987f393-8e1a-40ff-bbdd-88fc142b15c8', NOW(), NOW()),
('df351582-9638-40ca-a3a8-3a97b237e7c5', 'Rodrigo Barros', 'rodrigo.barros@taskflow.com', 'COLLABORATOR', '2284c16f-36cd-4d3d-a10c-0e85af0ca580', 'df351582-9638-40ca-a3a8-3a97b237e7c5', NOW(), NOW());

INSERT INTO employees (id, full_name, email, role, manager_id, user_id, created_at, updated_at) VALUES
('b008c55f-7237-40dd-8ed1-c68dd9373456', 'Patrícia Gomes', 'patricia.gomes@taskflow.com', 'COLLABORATOR', 'f774634b-5db7-474d-908b-c5ad6706808a', 'b008c55f-7237-40dd-8ed1-c68dd9373456', NOW(), NOW()),
('75c5d8b8-a15f-4897-82b4-d6b40dd13227', 'Lucas Ribeiro',  'lucas.ribeiro@taskflow.com',  'COLLABORATOR', 'f774634b-5db7-474d-908b-c5ad6706808a', '75c5d8b8-a15f-4897-82b4-d6b40dd13227', NOW(), NOW()),
('6c5bd507-26fd-4688-b105-e10eb07d4b67', 'Amanda Teixeira','amanda.teixeira@taskflow.com','COLLABORATOR', 'f774634b-5db7-474d-908b-c5ad6706808a', '6c5bd507-26fd-4688-b105-e10eb07d4b67', NOW(), NOW());