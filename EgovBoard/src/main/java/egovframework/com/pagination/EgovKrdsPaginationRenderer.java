/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package egovframework.com.pagination;

import jakarta.annotation.PostConstruct;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.AbstractKrdsPaginationRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class EgovKrdsPaginationRenderer extends AbstractKrdsPaginationRenderer {

	@Autowired
	private MessageSource messageSource;

	public EgovKrdsPaginationRenderer() {
	}

	@PostConstruct
	public void initMessageLabels() {
		// Initialize all labels with message properties
		initVariables();
		
		// Update labels with localized messages
		String prevLabel = messageSource.getMessage("page.prev", null, "이전", LocaleContextHolder.getLocale());
		String nextLabel = messageSource.getMessage("page.next", null, "다음", LocaleContextHolder.getLocale());

		previousPageLabel = "<a onclick=\"{0}({1})\" class=\"page-navi prev\" href=\"#\">" + prevLabel + "</a>";
		previousPageDisabledLabel = "<span class=\"page-navi prev disabled\">" + prevLabel + "</span>";
		nextPageLabel = "<a onclick=\"{0}({1})\" class=\"page-navi next\" href=\"#\">" + nextLabel + "</a>";
		nextPageDisabledLabel = "<span class=\"page-navi next disabled\">" + nextLabel + "</span>";
	}

	/**
	* PaginationRenderer
	*/
	public void initVariables() {
		// Initialize with default values (will be overridden by localized messages)
		previousPageLabel = "<a onclick=\"{0}({1})\" class=\"page-navi prev\" href=\"#\">이전</a>";
		previousPageDisabledLabel = "<span class=\"page-navi prev disabled\">이전</span>";
	    firstPageLabel = "<a onclick=\"{0}({1})\" class=\"page-link\" href=\"#\">{2}</a>";
	    currentPageLabel = "<a class=\"page-link active\" href=\"#\">{0}</a>";
	    otherPageLabel = "<a onclick=\"{0}({1})\" class=\"page-link\" href=\"#\">{2}</a>";
	    nextPageLabel = "<a onclick=\"{0}({1})\" class=\"page-navi next\" href=\"#\">다음</a>";
		nextPageDisabledLabel = "<span class=\"page-navi next disabled\">다음</span>";
	    lastPageLabel = "<a onclick=\"{0}({1})\" class=\"page-link\" href=\"#\">{2}</a>";
	    dotPageLabel = "<span class=\"page-link link-dot\">...</span>";	
	}

	/**
	 * Override renderPagination to ensure labels are updated with current locale
	 */
	@Override
	public String renderPagination(org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo paginationInfo, String jsFunction) {
		updateLabelsWithCurrentLocale();
		return super.renderPagination(paginationInfo, jsFunction);
	}

	/**
	 * Update labels with current locale
	 */
	private void updateLabelsWithCurrentLocale() {
		String prevLabel = messageSource.getMessage("page.prev", null, "이전", LocaleContextHolder.getLocale());
		String nextLabel = messageSource.getMessage("page.next", null, "다음", LocaleContextHolder.getLocale());

		previousPageLabel = "<a onclick=\"{0}({1})\" class=\"page-navi prev\" href=\"#\">" + prevLabel + "</a>";
		previousPageDisabledLabel = "<span class=\"page-navi prev disabled\">" + prevLabel + "</span>";
		nextPageLabel = "<a onclick=\"{0}({1})\" class=\"page-navi next\" href=\"#\">" + nextLabel + "</a>";
		nextPageDisabledLabel = "<span class=\"page-navi next disabled\">" + nextLabel + "</span>";
	}

}
