create table tabz.categoriasTab ( idTab int8 not null, categoria varchar(200) not null)

create table tabz.tabs (id int8 not null, atualizacao timestamp, link varchar(800) not null, nome varchar(300) not null, primary key (id))

alter table tabz.categoriasTab add constraint fk_tab foreign key (idTab) references tabz.tabs create sequence tabz.tabs_id_seq