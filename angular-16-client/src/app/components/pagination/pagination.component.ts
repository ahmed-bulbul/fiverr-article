import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent {
  @Input() currentPage: number =0;
  @Input() itemsPerPage: number=0;
  @Input() totalItems: number=0;
  @Output() pageChange = new EventEmitter<number>();

  totalPages: number = 0;

  ngOnChanges(): void {
    this.totalPages = Math.ceil(this.totalItems / this.itemsPerPage);
  }

  onPageClick(page: number): void {
    if (page >= 1 && page <= this.totalPages && page !== this.currentPage) {
      this.pageChange.emit(page);
    }
  }

  getPages(): number[] {
    return Array(this.totalPages).fill(0).map((_, index) => index + 1);
  }
}
