# sql-builder

## 关于sql-builder

[sql-builder](https://github.com/ibit-tech/sql-builder)尝试使用java对象，通过`类SQL`的拼接方式，动态快速的生成SQL。它可作为稍后的开源项目[ibit-mybatis](https://github.com/ibit-tech/ibit-mybatis)的核心类库。
sql-builder提供了对象拼接的构造类`Sql`和字符串拼接的构造类`StringSql`。

## Sql构造示例

详细测试用例查看：`tech.ibit.sqlbuilder.SqlTest`

### select

```
// 传入列
Sql sql = new Sql()
        .select(Arrays.asList(UserProperties.userId, UserProperties.name))
        .from(UserProperties.TABLE);
assertParamsEquals("SELECT u.user_id, u.name FROM user u", sql.getSqlParams());

// 支持聚合函数
sql = new Sql().select(Arrays.asList(new SumColumn(UserProperties.userId, "user_id_sum")
        , new AvgColumn(UserProperties.userId, "user_id_avg")))
        .from(UserProperties.TABLE).groupBy(UserProperties.userId);
assertParamsEquals("SELECT SUM(u.user_id) AS user_id_sum, AVG(u.user_id) AS user_id_avg FROM user u GROUP BY u.user_id"
        , sql.getSqlParams());

```

### selectDistinct

```
Sql sql = new Sql()
        .selectDistinct(UserProperties.email)
        .from(UserProperties.TABLE);
assertParamsEquals("SELECT DISTINCT u.email FROM user u", Collections.emptyList(), sql.getSqlParams());
```

### selectPo（解析传入类）

```
Sql sql = new Sql()
        .selectPo(UserPo.class)
        .from(UserProperties.TABLE);
assertParamsEquals("SELECT u.user_id, u.login_id, u.email, u.mobile_phone, u.type FROM user u",
        Collections.emptyList(), sql.getSqlParams());
```

### selectDistinctPo

```
Sql sql = new Sql()
        .selectDistinctPo(UserPo.class)
        .from(UserProperties.TABLE).limit(1000);
assertParamsEquals("SELECT DISTINCT u.user_id, u.login_id, u.email, u.mobile_phone, u.type FROM user u LIMIT ?, ?",
        Arrays.asList("$start", 0, "$limit", 1000), sql.getSqlParams());
```

### count

```
Sql sql = new Sql()
        .count()
        .from(UserProperties.TABLE);
assertParamsEquals("SELECT COUNT(*) FROM user u", sql.getSqlParams());
```

### countDistinct

```
// 传入单列
Sql sql = new Sql()
        .countDistinct(UserProperties.userId)
        .from(UserProperties.TABLE);
assertParamsEquals("SELECT COUNT(DISTINCT u.user_id) FROM user u", sql.getSqlParams());

// 传入多列
sql = new Sql()
        .countDistinct(Arrays.asList(UserProperties.name, UserProperties.email))
        .from(UserProperties.TABLE);
assertParamsEquals("SELECT COUNT(DISTINCT u.name, u.email) FROM user u", sql.getSqlParams());
```

### deleteFrom

```
// 删除操作必须包含where语句，不然这个操作很危险
Sql sql = new Sql().deleteFrom(UserProperties.TABLE);
thrown.expect(RuntimeException.class);
thrown.expectMessage("Where cannot be empty when do deleting!");
sql.getSqlParams();

// 正常删除
Sql sql = new Sql()
        .deleteFrom(UserProperties.TABLE)
        .andWhere(CriteriaItemMaker.equalsTo(UserProperties.userId, 1));
assertParamsEquals("DELETE FROM user WHERE user_id = ?",
        Arrays.asList("user_id", 1), sql.getSqlParams());
```

### deleteTableFrom（支持别名）

```
// 正常删除
Sql sql = new Sql()
        .deleteTableFrom(UserProperties.TABLE)
        .andWhere(CriteriaItemMaker.equalsTo(UserProperties.userId, 1));
assertParamsEquals("DELETE u.* FROM user u WHERE u.user_id = ?",
        Arrays.asList("u.user_id", 1), sql.getSqlParams());

// 支持join的删除
sql = new Sql()
        .deleteTableFrom(UserProperties.TABLE)
        .andWhere(CriteriaItemMaker.equalsTo(UserProperties.userId, 1))
        .leftJoinOn(OrganizationProperties.TABLE, Arrays.asList(UserProperties.orgId, OrganizationProperties.orgId));
        assertParamsEquals("DELETE u.* FROM user u LEFT JOIN organization o ON u.org_id = o.org_id WHERE u.user_id = ?",
                Arrays.asList("u.user_id", 1), sql.getSqlParams());
```

### update

```
// update操作必须包含where语句，不然这操作很危险
Sql sql = new Sql()
        .update(UserProperties.TABLE)
        .set(new ColumnValue(UserProperties.name, "IBIT"));
thrown.expect(RuntimeException.class);
thrown.expectMessage("Where cannot be empty when do updating!");
sql.getSqlParams();


// 正常删除
sql = new Sql()
    .update(UserProperties.TABLE)
    .set(new ColumnValue(UserProperties.name, "IBIT"))
    .andWhere(CriteriaItemMaker.equalsTo(UserProperties.userId, 1));
assertParamsEquals("UPDATE user u SET u.name = ? WHERE u.user_id = ?",
        Arrays.asList("u.name", "IBIT", "u.user_id", 1), sql.getSqlParams());

```

### insertInto & values

```
Sql sql = new Sql()
        .insertInto(UserProperties.TABLE)
        .values(Arrays.asList(new ColumnValue(UserProperties.name, "IBIT")
                , new ColumnValue(UserProperties.loginId, "188")
                , new ColumnValue(UserProperties.avatarId, null)));
assertParamsEquals("INSERT INTO user(name, login_id, avatar_id) VALUES(?, ?, ?)",
        Arrays.asList("name", "IBIT", "login_id", "188", "avatar_id", null), sql.getSqlParams());
```

### set

```
Sql sql = new Sql()
        .update(UserProperties.TABLE)
        .set(Arrays.asList(new ColumnValue(UserProperties.name, "IBIT")
                , new ColumnValue(UserProperties.loginId, "188")
                , new ColumnValue(UserProperties.avatarId, null)))
        .andWhere(CriteriaItemMaker.equalsTo(UserProperties.userId, 1));
assertParamsEquals("UPDATE user u SET u.name = ?, u.login_id = ?, u.avatar_id = ? WHERE u.user_id = ?",
        Arrays.asList("u.name", "IBIT", "u.login_id", "188", "u.avatar_id", null, "u.user_id", 1), sql.getSqlParams());
```

### increaseSet(字段增长）

```
Sql sql = new Sql()
        .update(UserProperties.TABLE)
        .increaseSet(new ColumnValue(UserProperties.loginTimes, 2))
        .andWhere(CriteriaItemMaker.equalsTo(UserProperties.userId, 1));
assertParamsEquals("UPDATE user u SET u.login_times = u.login_times + ? WHERE u.user_id = ?", 
        Arrays.asList("u.login_times", 2, "u.user_id", 1), sql.getSqlParams());
```


### decreaseSet(字段递减)

```
Sql sql = new Sql()
        .update(UserProperties.TABLE)
        .decreaseSet(new ColumnValue(UserProperties.loginTimes, 2))
        .andWhere(CriteriaItemMaker.equalsTo(UserProperties.userId, 1));
assertParamsEquals("UPDATE user u SET u.login_times = u.login_times - ? WHERE u.user_id = ?"
        , Arrays.asList("u.login_times", 2, "u.user_id", 1), sql.getSqlParams());
```

### from

```
Sql sql = new Sql()
        .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
        .from(UserProperties.TABLE)
        .from(ProjectProperties.TABLE)
        .andWhere(CriteriaItemMaker.equalsTo(UserProperties.currentProjectId, ProjectProperties.projectId));
assertParamsEquals("SELECT u.user_id, u.name, p.name FROM user u, project p WHERE u.current_project_id = p.project_id",
        Collections.emptyList(), sql.getSqlParams());

sql = new Sql()
        .select(Arrays.asList(ProjectProperties.projectId, ProjectProperties.name))
        .from(ProjectProperties.TABLE);
assertParamsEquals("SELECT p.project_id, p.name FROM project p",
        Collections.emptyList(), sql.getSqlParams());
```

### joinOn(left, right, full, inner)

```
Sql sql = new Sql()
        .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
        .from(UserProperties.TABLE)
        .joinOn(ProjectProperties.TABLE, Arrays.asList(UserProperties.currentProjectId, ProjectProperties.projectId));
assertParamsEquals("SELECT u.user_id, u.name, p.name FROM user u JOIN sz_project p ON u.current_project_id = p.project_id", sql.getSqlParams());

// left join on
sql = new Sql()
        .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
        .from(UserProperties.TABLE)
        .leftJoinOn(ProjectProperties.TABLE, Arrays.asList(UserProperties.currentProjectId, ProjectProperties.projectId));
assertParamsEquals("SELECT u.user_id, u.name, p.name FROM user u LEFT JOIN sz_project p ON u.current_project_id = p.project_id", sql.getSqlParams());

// 省略其他join
```

### complexJoinOn(支持on后面增加条件，left, right, full, inner)

```
        Sql sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
                .from(UserProperties.TABLE)
                .complexLeftJoinOn(ProjectProperties.TABLE, Collections.singletonList(CriteriaItemMaker.equalsTo(UserProperties.currentProjectId, ProjectProperties.projectId)));
        SqlParams sqlParams = sql.getSqlParams();
        assertEquals("SELECT u.user_id, u.name, p.name FROM user u LEFT JOIN project p ON u.current_project_id = p.project_id", sqlParams.getSql());
        assertTrue(sqlParams.getParamDetails().isEmpty());


        sql = new Sql()
                .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
                .from(UserProperties.TABLE)
                .complexLeftJoinOn(ProjectProperties.TABLE, Arrays.asList(
                        CriteriaItemMaker.equalsTo(UserProperties.currentProjectId, ProjectProperties.projectId),
                        CriteriaItemMaker.like(ProjectProperties.name, "小%")));
        sqlParams = sql.getSqlParams();
        assertEquals("SELECT u.user_id, u.name, p.name FROM user u LEFT JOIN project p "
                + "ON u.current_project_id = p.project_id AND p.name LIKE ?", sqlParams.getSql());
        assertList(sqlParams.getParamDetails(), 1, Arrays.asList("p.name", "小%"));
        
// 省略其他join
```

### where（支持构造复杂的where语句）

```
List<CriteriaItem> xiaoLikeItems = Arrays.asList(
        CriteriaItemMaker.like(UserProperties.name, "小%"),
        CriteriaItemMaker.like(UserProperties.email, "xiao%"));
CriteriaItem userIdItem = CriteriaItemMaker.greaterThan(UserProperties.userId, 100);
CriteriaItem type1Item = CriteriaItemMaker.equalsTo(UserProperties.type, 1);
CriteriaItem type2Item = CriteriaItemMaker.equalsTo(UserProperties.type, 2);

Sql sql = new Sql()
        .select(Arrays.asList(UserProperties.userId, UserProperties.name))
        .from(UserProperties.TABLE)
        .where(Criteria.ands(Arrays.asList(Criteria.ors(xiaoLikeItems), userIdItem)));
SqlParams sqlParams = sql.getSqlParams();
assertList(sqlParams.getParams(), 3, Arrays.asList("小%", "xiao%", 100));

sql = new Sql()
        .select(Arrays.asList(UserProperties.userId, UserProperties.name))
        .from(UserProperties.TABLE)
        .where(
                Criteria.ands(
                        Arrays.asList(Criteria.ors(xiaoLikeItems),
                                Criteria.ors(Collections.singletonList(userIdItem)))
                )
        );
sqlParams = sql.getSqlParams();
assertParamsEquals("SELECT u.user_id, u.name FROM user u WHERE (u.name LIKE ? OR u.email LIKE ?) AND u.user_id > ?",
        Arrays.asList("小%", "xiao%", 100), sqlParams);

sql = new Sql()
        .select(Arrays.asList(UserProperties.userId, UserProperties.name))
        .from(UserProperties.TABLE)
        .where(
                Criteria.ors(Arrays.asList(
                        Criteria.ands(
                                Arrays.asList(Criteria.ands(
                                        Arrays.asList(Criteria.ors(xiaoLikeItems),
                                                Criteria.ors(Collections.singletonList(userIdItem)))),
                                        type1Item)
                        ), type2Item))
        );
sqlParams = sql.getSqlParams();
assertParamsEquals("SELECT u.user_id, u.name FROM user u WHERE (((u.name LIKE ? OR u.email LIKE ?) AND u.user_id > ?) AND u.type = ?) OR u.type = ?",
        Arrays.asList("小%", "xiao%", 100, 1, 2), sqlParams);
```

### andWhere & orWhere & flag(支持标记位条件）

```
// and
Sql sql = new Sql()
            .select(Arrays.asList(UserProperties.userId, UserProperties.name))
            .from(UserProperties.TABLE)
            .andWhere(Criteria.ors(
                    Arrays.asList(CriteriaItemMaker.like(UserProperties.name, "小%"), CriteriaItemMaker.like(UserProperties.email, "xiao%"))))
                .limit(1);
        assertParamsEquals("SELECT u.user_id, u.name FROM user u WHERE (u.name LIKE ? OR u.email LIKE ?) LIMIT ?, ?"
                , Arrays.asList("u.name", "小%", "u.email", "xiao%", "$start", 0, "$limit", 1), sql.getSqlParams());

// or
sql = new Sql()
        .select(Arrays.asList(UserProperties.userId, UserProperties.name))
        .from(UserProperties.TABLE)
        .orWhere(Criteria.ands(
                Arrays.asList(CriteriaItemMaker.like(UserProperties.name, "小%"), CriteriaItemMaker.like(UserProperties.email, "xiao%"))))
        .orWhere(CriteriaItemMaker.equalsTo(UserProperties.type, 1))
        .limit(1);
assertParamsEquals("SELECT u.user_id, u.name FROM user u WHERE (u.name LIKE ? AND u.email LIKE ?) OR u.type = ? LIMIT ?, ?",
        Arrays.asList("u.name", "小%", "u.email", "xiao%", "u.type", 1, "$start", 0, "$limit", 1), sql.getSqlParams());

// CriteriaItemMaker支持构造flag有：containsNoneFlags, containsAllFlags, containsAnyFlags    
sql = new Sql()
        .select(Arrays.asList(UserProperties.userId, UserProperties.name))
        .from(UserProperties.TABLE)
        .andWhere(CriteriaItemMaker.containsNoneFlags(UserProperties.userId, 1));
assertParamsEquals("SELECT u.user_id, u.name FROM user u WHERE u.user_id & ? = 0",
        Arrays.asList("u.user_id", 1), sql.getSqlParams());       
```

### orderBy

```
Sql sql = new Sql()
        .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
        .from(UserProperties.TABLE)
        .leftJoinOn(ProjectProperties.TABLE, Arrays.asList(UserProperties.currentProjectId, ProjectProperties.projectId))
        .orderBy(Arrays.asList(new OrderBy(ProjectProperties.projectId), new OrderBy(UserProperties.userId, true)))
        .limit(1000);
assertParamsEquals("SELECT u.user_id, u.name, p.name FROM user u LEFT JOIN project p ON u.current_project_id = p.project_id ORDER BY p.project_id, u.user_id DESC LIMIT ?, ?",
        Arrays.asList("$start", 0, "$limit", 1000), sql.getSqlParams());
```

### customerOrderBy（自定义排序：mysql语法）

```
Sql sql = new Sql()
        .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
        .from(UserProperties.TABLE)
        .leftJoinOn(ProjectProperties.TABLE, Arrays.asList(UserProperties.currentProjectId, ProjectProperties.projectId))
        .orderBy(Arrays.asList(new OrderBy(ProjectProperties.projectId), new CustomOrderBy(UserProperties.userId, Arrays.asList(1, 2, 3), true)));
assertParamsEquals("SELECT u.user_id, u.name, p.name FROM user u LEFT JOIN project p ON u.current_project_id = p.project_id ORDER BY p.project_id"
        + ", FIELD(u.user_id, ?, ?, ?) DESC", Arrays.asList("u.user_id", 1, "u.user_id", 2, "u.user_id", 3), sql.getSqlParams());
```

### nameOrderBy（自定义字段排序）

```
Sql sql = new Sql()
        .select(
                Arrays.asList(
                        new MinColumn(UserProperties.age, "min_age"),
                        new MaxColumn(UserProperties.age, "max_age"),
                        UserProperties.gender
                ))
        .from(UserProperties.TABLE)
        .andWhere(CriteriaItemMaker.greaterThanOrEqualsTo(UserProperties.age, 0))
        .groupBy(UserProperties.gender)
        .having(Having.and(HavingItemMaker.greaterThanOrEqualsTo("min_age", 1)))
        .orderBy(Arrays.asList(
                new OrderBy(UserProperties.gender),
                new NameOrderBy("min_age", true)));
assertParamsEquals("SELECT MIN(u.age) AS min_age, MAX(u.age) AS max_age, u.gender FROM user u WHERE u.age >= ? " +
        "GROUP BY u.gender HAVING min_age >= ? ORDER BY u.gender, min_age DESC", Arrays.asList("u.age", 0, "min_age", 1), sql.getSqlParams());
```

### groupBy & having

```
Sql sql = new Sql()
        .select(
                Arrays.asList(
                        new MinColumn(UserProperties.age, "min_age"),
                        new MaxColumn(UserProperties.age, "max_age"),
                        UserProperties.gender
                ))
        .from(UserProperties.TABLE)
        .andWhere(CriteriaItemMaker.greaterThanOrEqualsTo(UserProperties.age, 0))
        .groupBy(UserProperties.gender)
        .having(Having.and(HavingItemMaker.greaterThanOrEqualsTo("min_age", 1)));
assertParamsEquals("SELECT MIN(u.age) AS min_age, MAX(u.age) AS max_age, u.gender FROM user u WHERE u.age >= ? GROUP BY u.gender HAVING min_age >= ?",
        Arrays.asList("u.age", 0, "min_age", 1), sql.getSqlParams());
```

### andHaving & orHaving

```
// andHaving
sql = new Sql()
        .select(
                Arrays.asList(
                        new MinColumn(UserProperties.age, "min_age"),
                        new MaxColumn(UserProperties.age, "max_age"),
                        UserProperties.gender
                ))
        .from(UserProperties.TABLE)
        .andWhere(CriteriaItemMaker.greaterThanOrEqualsTo(UserProperties.age, 0))
        .groupBy(UserProperties.gender)
        .andHaving(
                Having.ors(
                        Arrays.asList(
                                HavingItemMaker.greaterThanOrEqualsTo("min_age", 1),
                                HavingItemMaker.greaterThanOrEqualsTo("max_age", 2))
                ))
        .andHaving(
                Having.ors(
                        Arrays.asList(
                                HavingItemMaker.greaterThanOrEqualsTo("min_age", 3),
                                HavingItemMaker.greaterThanOrEqualsTo("max_age", 4)
                        ))
        );
assertParamsEquals("SELECT MIN(u.age) AS min_age, MAX(u.age) AS max_age, u.gender FROM user u WHERE u.age >= ? GROUP BY u.gender "
                + "HAVING (min_age >= ? OR max_age >= ?) AND (min_age >= ? OR max_age >= ?)",
        Arrays.asList("u.age", 0, "min_age", 1, "max_age", 2, "min_age", 3, "max_age", 4), sql.getSqlParams());


// orHaving
sql = new Sql()
        .select(
                Arrays.asList(
                        new MinColumn(UserProperties.age, "min_age"),
                        new MaxColumn(UserProperties.age, "max_age"),
                        UserProperties.gender
                ))
        .from(UserProperties.TABLE)
        .andWhere(CriteriaItemMaker.greaterThanOrEqualsTo(UserProperties.age, 0))
        .groupBy(UserProperties.gender)
        .orHaving(
                Having.ands(
                        Arrays.asList(
                                HavingItemMaker.greaterThanOrEqualsTo("min_age", 1),
                                HavingItemMaker.greaterThanOrEqualsTo("max_age", 2))
                ))
        .orHaving(
                Having.ands(
                        Arrays.asList(
                                HavingItemMaker.greaterThanOrEqualsTo("min_age", 3),
                                HavingItemMaker.greaterThanOrEqualsTo("max_age", 4)
                        ))
        );
assertParamsEquals("SELECT MIN(u.age) AS min_age, MAX(u.age) AS max_age, u.gender FROM user u WHERE u.age >= ? GROUP BY u.gender "
                + "HAVING (min_age >= ? AND max_age >= ?) OR (min_age >= ? AND max_age >= ?)",
        Arrays.asList("u.age", 0, "min_age", 1, "max_age", 2, "min_age", 3, "max_age", 4), sql.getSqlParams());

```

### limit

```
Sql sql = new Sql()
        .select(Arrays.asList(UserProperties.userId, UserProperties.name, ProjectProperties.name))
        .from(UserProperties.TABLE)
        .leftJoinOn(ProjectProperties.TABLE, Arrays.asList(UserProperties.currentProjectId, ProjectProperties.projectId))
        .orderBy(Arrays.asList(new OrderBy(ProjectProperties.projectId), new OrderBy(UserProperties.userId, true)))
        .limit(10);
assertParamsEquals("SELECT u.user_id, u.name, p.name FROM user u LEFT JOIN project p ON u.current_project_id = p.project_id ORDER BY p.project_id"
        + ", u.user_id DESC LIMIT ?, ?", Arrays.asList("$start", 0, "$limit", 10), sql.getSqlParams());
```

## StringSql构造

构造方式与Sql相似，可以查看：`tech.ibit.sqlbuilder.StringSqlTest`

## 相关maven依赖包

```
<dependency>
  <groupId>tech.ibit</groupId>
  <artifactId>sql-builder</artifactId>
  <version>1.1</version>
</dependency>
```


版权声明: [Apache 2](http://www.apache.org/licenses/LICENSE-2.0.txt)