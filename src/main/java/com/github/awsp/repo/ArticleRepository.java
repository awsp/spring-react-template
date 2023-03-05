package com.github.awsp.repo;

import com.github.awsp.model.Article;

public interface ArticleRepository extends SoftDeleteCRUDRepository<Article, Long> {
}
