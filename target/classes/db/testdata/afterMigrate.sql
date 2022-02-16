insert ignore into cozinha (id, nome) values (1, 'Tailandesa');
insert ignore into cozinha (id, nome) values (2, 'Indiana');
insert ignore into cozinha (id, nome) values (3, 'Argentina');
insert ignore into cozinha (id, nome) values (4, 'Brasileira');

insert ignore into estado (id, nome) values (1, 'Minas Gerais');
insert ignore into estado (id, nome) values (2, 'São Paulo');
insert ignore into estado (id, nome) values (3, 'Ceará');

insert ignore into cidade (id, nome, estado_id) values (1, 'Uberlândia', 1);
insert ignore into cidade (id, nome, estado_id) values (2, 'Belo Horizonte', 1);
insert ignore into cidade (id, nome, estado_id) values (3, 'São Paulo', 2);
insert ignore into cidade (id, nome, estado_id) values (4, 'Campinas', 2);
insert ignore into cidade (id, nome, estado_id) values (5, 'Fortaleza', 3);

update restaurante set ativo = true where id in (1,2,3,4,5,6);
update restaurante set  endereco_cidade_id = 1,  endereco_cep = '04205-000', endereco_logradouro = 'Rua Bom Pastor', endereco_numero = 955, endereco_complemento = 'apto 14', endereco_bairro = 'Ipiranga' where id in (1,2,3,4,5,6);

insert ignore into forma_pagamento (id, descricao) values (1, 'Cartão de crédito');
insert ignore into forma_pagamento (id, descricao) values (2, 'Cartão de débito');
insert ignore into forma_pagamento (id, descricao) values (3, 'Dinheiro');

insert ignore into permissao (id, nome, descricao) values (1, 'CONSULTAR_COZINHAS', 'Permite consultar cozinhas');
insert ignore into permissao (id, nome, descricao) values (2, 'EDITAR_COZINHAS', 'Permite editar cozinhas');

insert ignore into restaurante_forma_pagamento (restaurante_id, forma_pagamento_id) values (1, 1), (1, 2), (1, 3), (2, 3), (3, 2), (3, 3), (4, 1), (4, 2), (5, 1), (5, 2), (6, 3);

insert ignore into produto (nome, descricao, preco, ativo, restaurante_id) values ('Porco com molho agridoce', 'Deliciosa carne suína ao molho especial', 78.90, 1, 1);
insert ignore into produto (nome, descricao, preco, ativo, restaurante_id) values ('Camarão tailandês', '16 camarões grandes ao molho picante', 110, 1, 1);
insert ignore into produto (nome, descricao, preco, ativo, restaurante_id) values ('Salada picante com carne grelhada', 'Salada de folhas com cortes finos de carne bovina grelhada e nosso molho especial de pimenta vermelha', 87.20, 1, 2);
insert ignore into produto (nome, descricao, preco, ativo, restaurante_id) values ('Garlic Naan', 'Pão tradicional indiano com cobertura de alho', 21, 1, 3);
insert ignore into produto (nome, descricao, preco, ativo, restaurante_id) values ('Murg Curry', 'Cubos de frango preparados com molho curry e especiarias', 43, 1, 3);
insert ignore into produto (nome, descricao, preco, ativo, restaurante_id) values ('Bife Ancho', 'Corte macio e suculento, com dois dedos de espessura, retirado da parte dianteira do contrafilé', 79, 1, 4);
insert ignore into produto (nome, descricao, preco, ativo, restaurante_id) values ('T-Bone', 'Corte muito saboroso, com um osso em formato de T, sendo de um lado o contrafilé e do outro o filé mignon', 89, 1, 4);
insert ignore into produto (nome, descricao, preco, ativo, restaurante_id) values ('Sanduíche X-Tudo', 'Sandubão com muito queijo, hamburger bovino, bacon, ovo, salada e maionese', 19, 1, 5);
insert ignore into produto (nome, descricao, preco, ativo, restaurante_id) values ('Espetinho de Cupim', 'Acompanha farinha, mandioca e vinagrete', 8, 1, 6);

insert ignore into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
	                status, data_criacao, subtotal, taxa_frete, valor_total)
values (1, 'f9981ca4-5a5e-4da3-af04-933861df3e55', 1, 8, 1, 1, '38400-000', 'Rua Floriano Peixoto', '500', 'Apto 801', 'Brasil',
        'CRIADO', utc_timestamp, 298.90, 10, 308.90);

insert ignore into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (1, 1, 1810, 1, 78.9, 78.9, null);

insert ignore into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (2, 1, 1811, 2, 110, 220, 'Menos picante, por favor');


insert ignore into pedido (id, codigo, restaurante_id, usuario_cliente_id, forma_pagamento_id, endereco_cidade_id, endereco_cep,
                    endereco_logradouro, endereco_numero, endereco_complemento, endereco_bairro,
	                status, data_criacao, subtotal, taxa_frete, valor_total)
values (2, 'd178b637-a785-4768-a3cb-aa1ce5a8cdab', 7, 7, 2, 1, '38400-111', 'Rua Acre', '300', 'Casa 2', 'Centro',
        'CRIADO', utc_timestamp, 79, 0, 79);

insert ignore into item_pedido (id, pedido_id, produto_id, quantidade, preco_unitario, preco_total, observacao)
values (3, 2, 1810, 1, 79, 79, 'Ao ponto');

