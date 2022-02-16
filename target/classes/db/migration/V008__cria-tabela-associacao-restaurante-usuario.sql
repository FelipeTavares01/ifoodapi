create table restaurante_usuario (
	restaurante_id bigint not null,
	usuario_id bigint not null,

	primary key (restaurante_id, usuario_id)
) engine=InnoDB default charset=utf8;

alter table restaurante_usuario add constraint fk_rest_usuario
foreign key (usuario_id) references usuario (id);

alter table restaurante_usuario add constraint fk_rest_usuario_restaurante
foreign key (restaurante_id) references restaurante (id);