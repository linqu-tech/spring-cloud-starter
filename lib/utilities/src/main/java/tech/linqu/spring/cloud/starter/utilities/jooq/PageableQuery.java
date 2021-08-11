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
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.jooq.Field;
import org.jooq.Record;
import org.jooq.SelectConnectByStep;
import org.jooq.SortField;
import org.jooq.Table;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Create pageable query.
 *
 * @param <T> type of {@link Record}
 */
@AllArgsConstructor(staticName = "of")
public class PageableQuery<T extends Record> {

    private final Table<T> table;

    private final Pageable pageable;

    private final SelectConnectByStep<T> dataQuery;

    private final SelectConnectByStep<? extends Record> countQuery;

    /**
     * Fetch a page.
     *
     * @return {@link Page}
     */
    public Page<T> fetch() {
        int count = Optional.ofNullable(countQuery.fetchOne(0, Integer.class)).orElse(0);
        Pageable pageable = this.pageable;
        if (pageable.isUnpaged()) {
            dataQuery.limit(20);
            List<T> data = dataQuery.fetch();
            return new PageImpl<>(data, pageable, count);
        }
        if (count < pageable.getOffset()) {
            int page = count / pageable.getPageSize();
            pageable = PageRequest.of(page, pageable.getPageSize(), pageable.getSort());
        }
        this.applyPageable(pageable);
        List<T> data = dataQuery.fetch();
        int total = (int) pageable.getOffset() + data.size();
        return new PageImpl<>(data, pageable, Math.max(total, count));
    }

    private void applyPageable(Pageable pageable) {
        if (pageable.getSort() != Sort.unsorted()) {
            dataQuery.orderBy(getSortFields(table, pageable.getSort()));
        }
        dataQuery.limit(pageable.getPageSize()).offset(pageable.getOffset());
    }

    private Collection<SortField<?>> getSortFields(Table<?> table, Sort sortSpecification) {
        Collection<SortField<?>> querySortFields = new ArrayList<>();
        for (Sort.Order specifiedField : sortSpecification) {
            String sortFieldName = specifiedField.getProperty();
            Sort.Direction sortDirection = specifiedField.getDirection();

            Field<?> field = table.field(sortFieldName);
            if (field != null) {
                SortField<?> querySortField = convertTableFieldToSortField(field, sortDirection);
                querySortFields.add(querySortField);
            }
        }

        return querySortFields;
    }

    private SortField<?> convertTableFieldToSortField(Field<?> field,
                                                      Sort.Direction sortDirection) {
        return sortDirection == Sort.Direction.ASC ? field.asc() : field.desc();
    }
}
