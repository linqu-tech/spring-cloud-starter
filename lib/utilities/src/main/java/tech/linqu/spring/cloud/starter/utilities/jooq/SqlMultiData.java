/*
 * Copyright (c) 2020 linqu.tech, All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tech.linqu.spring.cloud.starter.utilities.jooq;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import lombok.Getter;
import org.jooq.Field;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.TableRecord;

/**
 * Multi values sql wrapper.
 */
@Getter
public class SqlMultiData {

    /**
     * An EMPTY instance of {@link SqlMultiData}.
     */
    public static SqlMultiData EMPTY = new SqlMultiData(null, 0, 0);

    private final Table<?> table;

    private final List<Field<?>> keys;

    private final List<List<Object>> valuesList;

    private int fieldSize;

    /**
     * Constructor.
     *
     * @param table      {@link Table}
     * @param columnSize column size
     * @param rowSize    row size
     */
    private SqlMultiData(Table<?> table, int columnSize, int rowSize) {
        this.table = table;
        this.keys = new ArrayList<>(columnSize);
        this.valuesList = new ArrayList<>(rowSize);
    }

    /**
     * Create a {@link SqlMultiData}.
     *
     * @param records  list of records
     * @param excludes exclude fields
     * @return {@link SqlMultiData}
     */
    public static SqlMultiData of(Collection<? extends TableRecord<?>> records,
                                  List<TableField<?, ?>> excludes) {
        if (records == null || records.isEmpty()) {
            return EMPTY;
        }
        boolean[] skip = null;
        SqlMultiData data = null;
        for (TableRecord<?> record : records) {
            if (skip == null) {
                skip = new boolean[record.size()];
                data = new SqlMultiData(record.getTable(), record.size(), records.size());
                for (int i = 0; i < record.fields().length; i++) {
                    Field<?> field = record.field(i);
                    skip[i] = excludes != null
                        && excludes.stream().anyMatch(e -> e.getName().equals(field.getName()));
                    if (!skip[i]) {
                        data.keys.add(field);
                        data.fieldSize++;
                    }
                }
            }
            List<Object> values = data.createValues();
            for (int i = 0; i < record.fields().length; i++) {
                if (!skip[i]) {
                    values.add(record.get(i));
                }
            }
        }
        return data;
    }

    private List<Object> createValues() {
        List<Object> values = new ArrayList<>(fieldSize);
        valuesList.add(values);
        return values;
    }
}
