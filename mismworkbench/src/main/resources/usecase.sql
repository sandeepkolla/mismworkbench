select containers0_.container_id as containe2_2_0_, 
containers0_.usecase_id as usecase_1_2_0_, 
container1_.id as id1_0_1_, 
container1_.descr as descr2_0_1_, 
container1_.name as name3_0_1_ 
from `usecase-container` containers0_ 
inner join container container1_ on containers0_.usecase_id=container1_.id where containers0_.container_id=?