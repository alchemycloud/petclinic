export class PagedDto<T> {

  results: Array<T>;
  totalCount: number;

  constructor(results: Array<T>, totalCount: number) {
    this.results = results;
    this.totalCount = totalCount;
  }
}
