INSERT INTO public.state(
  id,
  state)
VALUES
  (1, 'Реализуется с отклонением по срокам');
INSERT INTO public.state(
  id,
  state)
VALUES
  (2, 'Реализуется с отклонением по бюджету');
INSERT INTO public.state(
  id,
  state)
VALUES
  (3, 'Реализуется без отклонений');
 INSERT INTO public.state(
  id,
  state)
VALUES
  (4, 'Завершен - все работы выполнены');

INSERT INTO public.project(
  id,
  name,
  fb,
  rb,
  id_state,
  mid_pr)
VALUES ( 1, 'Проект 1', 200, 200, 1, 0.60);

-- Реализуется с отклонением по бюджету
INSERT INTO public.project(
  id, 
  name, 
  fb, 
  rb, 
  id_state,
  mid_pr)
VALUES ( 2, 'Проект 2', 300, 300, 2, 0.90);

INSERT INTO public.project(
  id, 
  name, 
  fb, 
  rb, 
  id_state,
  mid_pr)
VALUES ( 3, 'Проект 3', 320, 320, 3, 0.10);

INSERT INTO public.project(
  id, 
  name, 
  fb, 
  rb, 
  id_state,
  mid_pr)
VALUES ( 4, 'Проект 4', 150, 150, 4, 1);

-- 10 дней лимит, а работа еще не завершенаx
INSERT INTO public.job(
  id,
  id_project,
  name,
  fb, 
  rb, 
  nach, 
  okonch, 
  dliteln, 
  vip_pr)
VALUES ( 1, 1, 'работа 1 проекта 1', 100, 100, '2018-10-01', null, 10, 0.2 );

INSERT INTO public.job(
  id,
  id_project,
  name,
  fb, 
  rb, 
  nach, 
  okonch, 
  dliteln, 
  vip_pr)
VALUES ( 2, 1,  'работа 2 проекта 1', 100, 100, '2018-10-01', '2018-10-10', 3, 1 );

--Реализуется с отклонением по бюджету - есть работы с нераспределенным бюджетом
-- 300 всего, одна работа с 200
INSERT INTO public.job(
  id,
  id_project,
  name,
  fb, 
  rb, 
  nach, 
  okonch, 
  dliteln, 
  vip_pr)
VALUES ( 3, 2,  'работа 1 проекта 2', 200, 200, '2018-10-10', null, 20, 0.4 );

-- Реализуется без отклонений
INSERT INTO public.job(
  id,
  id_project,  
  name,
  fb, 
  rb, 
  nach, 
  okonch, 
  dliteln, 
  vip_pr)
VALUES ( 4, 3,  'работа 1 проекта 3', 320, 320, '2018-10-10', null, 9, 0.8 );

-- Завершен - все работы выполнены
INSERT INTO public.job(
  id,
  id_project,  
  name,
  fb, 
  rb, 
  nach, 
  okonch, 
  dliteln, 
  vip_pr)
VALUES ( 5, 4,  'работа 1 проекта 4', 150, 150, '2018-10-01', '2018-10-03', 2, 1 );
