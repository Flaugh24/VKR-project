-- Заполнение первичных данных базы ВКР
-- Таблицы заполнения:
--

--------------------------------------------------------------------------------------
DELETE FROM public.student_educ_programs;
DELETE FROM public.role_members;
DELETE FROM public.role_permissions;

DELETE FROM public.roles;
DELETE FROM public.permissions;


DELETE FROM public.ticket;
DELETE FROM public.educ_program;
DELETE FROM public.users;
DELETE FROM public.document_type;
DELETE FROM public.status;
DELETE FROM public.type_of_use;




--------------------------------------------------------------------------------------

INSERT INTO public.users(
  id, email, enabled, first_name, first_name_eng, password, second_name, second_name_eng, surname, surname_eng, tel_number, username)
VALUES (1, 'admin@admin', true, 'Иван', 'Ivan',
           'admin', 'Юрьевич', 'x', 'Гагаркин', 'Gagarkin', null, 'admin');


--------------------------------------------------------------------------------------

INSERT INTO public.status(
  id, name)
VALUES (1, 'Создана');

INSERT INTO public.status(
  id, name)
VALUES (2, 'На проверке');

INSERT INTO public.status(
  id, name)
VALUES (3, 'Проверенные');

INSERT INTO public.status(
  id, name)
VALUES (4, 'На передачу в ЭБ');

INSERT INTO public.status(
  id, name)
VALUES (5, 'Возвращена на доработку');

INSERT INTO public.status(
  id, name)
VALUES (6, 'Возвращена обработке в ЭБ');

INSERT INTO public.status(
  id, name)
VALUES (7, 'Требует доработки');

--------------------------------------------------------------------------------------

INSERT INTO public.type_of_use(
  id, type)
VALUES (1, 'Чтение');

INSERT INTO public.type_of_use(
  id, type)
VALUES (2, 'Чтение и печать');

INSERT INTO public.type_of_use(
  id, type)
VALUES (3, 'Чтение, печать, копирование');

--------------------------------------------------------------------------------------

INSERT INTO public.document_type(
  id, name, name_eng)
VALUES (1, 'Бакалаврская работа', 'Bachalor work');

INSERT INTO public.document_type(
  id, name, name_eng)
VALUES (2, 'Магистерская диссертация', 'Masters dissertation');

INSERT INTO public.document_type(
  id, name, name_eng)
VALUES (3, 'Доклад', 'Report');

--------------------------------------------------------------------------------------

--student_educ_programs: student_id 1 educ_student_id 1
-- 1 2

--------------------------------------------------------------------------------------

INSERT INTO public.permissions(
  id, name)
VALUES (1, 'PERM_STUDENT_PAGE');

INSERT INTO public.permissions(
  id, name)
VALUES (2, 'PERM_COODRINATOR_PAGE');

INSERT INTO public.permissions(
  id, name)
VALUES (3, 'PERM_BIBLIOGRAPHER_PAGE');

--------------------------------------------------------------------------------------

INSERT INTO public.roles(
  id, name)
VALUES (1, 'student');

INSERT INTO public.roles(
  id, name)
VALUES (2, 'coordinator');

INSERT INTO public.roles(
  id, name)
VALUES (3, 'bibliographer');

------------

INSERT INTO public.educ_program(
  id, degree, direction, group_num, institute, specialty)
VALUES (1, 'Магистр', 'asd', '6', 'dsada', 'qwerty');

INSERT INTO public.educ_program(
  id, degree, direction, group_num, institute, specialty)
VALUES (2, 'Бакалавр', 'asd', '4', 'dsada', 'qwerty');

--------------------------------------------------

INSERT INTO public.student_educ_programs(
  student_id, educ_program_id)
VALUES (1, 1);

INSERT INTO public.student_educ_programs(
  student_id, educ_program_id)
VALUES (1, 2);

--------------------------------------------------------------------------------------

 INSERT INTO public.role_members(
   members_id, roles_id)
 VALUES (1, 1);

 --INSERT INTO public.role_members(
 --  members_id, roles_id)
 --VALUES (2, 2);
--
-- INSERT INTO public.role_members(
--   members_id, roles_id)
-- VALUES (3, 3);

--------------------------------------------------------------------------------------

 INSERT INTO public.role_permissions(
   roles_id, permissions_id)
 VALUES (1, 1);
--
 INSERT INTO public.role_permissions(
   roles_id, permissions_id)
 VALUES (1, 2);
--
 INSERT INTO public.role_permissions(
   roles_id, permissions_id)
 VALUES (1, 3);
--

--
--

--

--
 INSERT INTO public.role_permissions(
   roles_id, permissions_id)
 VALUES (2, 2);
--

--
 INSERT INTO public.role_permissions(
   roles_id, permissions_id)
 VALUES (3, 2);
--
 INSERT INTO public.role_permissions(
   roles_id, permissions_id)
 VALUES (3, 3);


-----------------------------------------------------------------------------------


