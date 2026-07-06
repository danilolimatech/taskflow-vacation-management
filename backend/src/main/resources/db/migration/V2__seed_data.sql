INSERT INTO users (id, username, password, role, created_at, updated_at) VALUES
('33d1c2ad-7ced-4871-834c-f75a796306b6', 'admin.maribel',  '$2a$10$0/5rdnmjxqOqq335bQkiy.jVLu0sxLUAVl3NVdLh6r8FsotMnuw92', 'ADMIN', NOW(), NOW()),
('93e053ed-9b6a-4eca-8301-bae13508ecac', 'admin.nuno',     '$2a$10$0/5rdnmjxqOqq335bQkiy.jVLu0sxLUAVl3NVdLh6r8FsotMnuw92', 'ADMIN', NOW(), NOW());

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

INSERT INTO vacations (id, employee_id, start_date, end_date, status, created_at, updated_at) VALUES
('a1b2c3d4-0001-0001-0001-000000000001', '36d0b929-d939-4ed9-a884-1e5aeae06e80', '2025-07-14', '2025-07-25', 'APPROVED',  NOW(), NOW()),
('a1b2c3d4-0001-0001-0001-000000000002', 'ba741a13-8be2-4167-81cd-9d32667fb45c', '2025-08-04', '2025-08-15', 'APPROVED',  NOW(), NOW()),
('a1b2c3d4-0001-0001-0001-000000000003', 'd5ac7441-a3b9-44a7-8ebc-1326426c68bd', '2025-09-01', '2025-09-12', 'REJECTED',  NOW(), NOW()),
('a1b2c3d4-0001-0001-0001-000000000004', 'b54ad0ed-a9fe-4cf8-879d-dfbb70a3b067', '2025-09-15', '2025-09-26', 'PENDING',   NOW(), NOW()),
('a1b2c3d4-0001-0001-0001-000000000005', '4410e9a5-105b-4346-83bd-af97723f6782', '2025-10-06', '2025-10-17', 'PENDING',   NOW(), NOW()),
('a1b2c3d4-0001-0001-0001-000000000006', 'f85b39f4-5b25-4df1-91cf-95e4c4fc4c94', '2025-10-20', '2025-10-31', 'APPROVED',  NOW(), NOW()),
('a1b2c3d4-0001-0001-0001-000000000007', '2e9a07cc-43d7-446a-bbb9-f0aafd2a84fd', '2025-11-03', '2025-11-14', 'PENDING',   NOW(), NOW()),
('a1b2c3d4-0001-0001-0001-000000000008', '0987f393-8e1a-40ff-bbdd-88fc142b15c8', '2025-11-17', '2025-11-28', 'REJECTED',  NOW(), NOW()),
('a1b2c3d4-0001-0001-0001-000000000009', 'df351582-9638-40ca-a3a8-3a97b237e7c5', '2025-12-01', '2025-12-12', 'APPROVED',  NOW(), NOW()),
('a1b2c3d4-0001-0001-0001-000000000010', 'b008c55f-7237-40dd-8ed1-c68dd9373456', '2026-01-05', '2026-01-16', 'PENDING',   NOW(), NOW()),
('a1b2c3d4-0001-0001-0001-000000000011', '75c5d8b8-a15f-4897-82b4-d6b40dd13227', '2026-01-19', '2026-01-30', 'APPROVED',  NOW(), NOW()),
('a1b2c3d4-0001-0001-0001-000000000012', '6c5bd507-26fd-4688-b105-e10eb07d4b67', '2026-02-02', '2026-02-13', 'PENDING',   NOW(), NOW());

INSERT INTO vacations (id, employee_id, start_date, end_date, status, created_at, updated_at) VALUES
('a1b2c3d4-0002-0002-0002-000000000001', '36d0b929-d939-4ed9-a884-1e5aeae06e80', '2026-03-02', '2026-03-13', 'PENDING',  NOW(), NOW()),
('a1b2c3d4-0002-0002-0002-000000000002', '36d0b929-d939-4ed9-a884-1e5aeae06e80', '2026-06-01', '2026-06-12', 'APPROVED', NOW(), NOW()),
('a1b2c3d4-0002-0002-0002-000000000003', 'ba741a13-8be2-4167-81cd-9d32667fb45c', '2026-03-16', '2026-03-27', 'REJECTED', NOW(), NOW()),
('a1b2c3d4-0002-0002-0002-000000000004', 'ba741a13-8be2-4167-81cd-9d32667fb45c', '2026-07-06', '2026-07-17', 'PENDING',  NOW(), NOW()),
('a1b2c3d4-0002-0002-0002-000000000005', 'd5ac7441-a3b9-44a7-8ebc-1326426c68bd', '2026-04-06', '2026-04-17', 'APPROVED', NOW(), NOW()),
('a1b2c3d4-0002-0002-0002-000000000006', 'f85b39f4-5b25-4df1-91cf-95e4c4fc4c94', '2026-04-20', '2026-05-01', 'PENDING',  NOW(), NOW()),
('a1b2c3d4-0002-0002-0002-000000000007', '4410e9a5-105b-4346-83bd-af97723f6782', '2026-05-04', '2026-05-15', 'APPROVED', NOW(), NOW()),
('a1b2c3d4-0002-0002-0002-000000000008', 'b54ad0ed-a9fe-4cf8-879d-dfbb70a3b067', '2026-05-18', '2026-05-29', 'REJECTED', NOW(), NOW()),
('a1b2c3d4-0002-0002-0002-000000000009', '2e9a07cc-43d7-446a-bbb9-f0aafd2a84fd', '2026-06-15', '2026-06-26', 'APPROVED', NOW(), NOW()),
('a1b2c3d4-0002-0002-0002-000000000010', '0987f393-8e1a-40ff-bbdd-88fc142b15c8', '2026-07-20', '2026-07-31', 'PENDING',  NOW(), NOW()),
('a1b2c3d4-0002-0002-0002-000000000011', 'df351582-9638-40ca-a3a8-3a97b237e7c5', '2026-08-03', '2026-08-14', 'PENDING',  NOW(), NOW()),
('a1b2c3d4-0002-0002-0002-000000000012', '75c5d8b8-a15f-4897-82b4-d6b40dd13227', '2026-08-17', '2026-08-28', 'REJECTED', NOW(), NOW()),
('a1b2c3d4-0002-0002-0002-000000000013', '6c5bd507-26fd-4688-b105-e10eb07d4b67', '2026-09-07', '2026-09-18', 'APPROVED', NOW(), NOW()),
('a1b2c3d4-0002-0002-0002-000000000014', 'b008c55f-7237-40dd-8ed1-c68dd9373456', '2026-09-21', '2026-10-02', 'PENDING',  NOW(), NOW()),
('a1b2c3d4-0002-0002-0002-000000000015', 'df351582-9638-40ca-a3a8-3a97b237e7c5', '2026-10-05', '2026-10-16', 'APPROVED', NOW(), NOW());
