ALTER TABLE pigs
    ADD CONSTRAINT fk_pigs foreign key (user_id) references pig.public.users (id);