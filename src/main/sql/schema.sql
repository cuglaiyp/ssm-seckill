DROP database if exists myseckill;

CREATE database myseckill default charset = utf8;

use myseckill;

create table seckill(
                        seckill_id bigint not null auto_increment comment '商品id',
                        name varchar(120) not null comment '商品名称',
                        number int not null comment '库存数量',
                        start_time timestamp not null  comment '秒杀开启时间',
                        end_time timestamp not null comment '秒杀结束时间',
                        create_time timestamp not null default current_timestamp comment '创建时间',
                        primary key (seckill_id),
                        key idx_start_time(start_time),
                        key idx_end_time(end_time),
                        key idx_create_time(create_time)
)engine = InnoDB auto_increment = 1000 comment ='数据库秒杀表';

insert into
    seckill(name, number, start_time, end_time)
values
('2000元秒杀iPhone12','100','2020-12-24','2020-12-25'),
('800元秒杀iPad8','50','2020-12-24','2020-12-25'),
('1000元秒杀Mate30','200','2020-12-24','2020-12-25'),
('500元秒杀iPhone6','100','2020-12-24','2020-12-25');


create table success_killed(
                               seckill_id bigint not null comment '秒杀商品id',
                               user_phone bigint not null comment '用户手机号',
                               state tinyint not null default -1 comment '状态标识：-1无效 0:成功 1:已付款',
                               create_time timestamp not null comment '创建时间',
                               primary key (seckill_id, user_phone),
                               key idx_create_time(create_time)
)engine InnoDB comment '秒杀成功明细表';


select @@sql_mode;

set sql_mode = 'STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

select @@global.sql_mode;

set global sql_mode = 'STRICT_TRANS_TABLES,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION'