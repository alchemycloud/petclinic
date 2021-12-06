import {PetType} from '../../../services/backend/enums';
import {PagedDto} from '../../../services/backend/pagedDto';
import {PetApiService, PetsRequest, PetsResponse} from '../../../services/backend/petApi.service';
import {
  AfterViewInit,
  Component,
  EventEmitter,
  Input,
  OnChanges,
  OnInit,
  Output,
  SimpleChanges,
  ViewChild
} from '@angular/core';
import {MatPaginator} from '@angular/material/paginator';
import {Router} from '@angular/router';

export class PetsListModel {
  id: number;
  name: string;
  petType: PetType;
  userLastName: string;

  constructor(id: number,
              name: string,
              petType: PetType,
              userLastName: string) {
    this.id = id;
    this.name = name;
    this.petType = petType;
    this.userLastName = userLastName;
  }

}

export class Selected {
  itemId: number;

  constructor(itemId: number) {
    this.itemId = itemId;
  }

}

@Component({
  selector: 'pets-list',
  templateUrl: './petsList.list.html',
  styleUrls: ['./petsList.list.scss']
})
export class PetsList implements OnChanges, OnInit, AfterViewInit {
  resultsLength = 0;
  @Input() drop: number = null;
  @Input() take: number = null;
  model: Array<PetsListModel> = [];
  @Output() onSelected = new EventEmitter<Selected>();
  @ViewChild(MatPaginator) matPaginator: MatPaginator;

  constructor(private readonly petApi: PetApiService, private readonly router: Router) {
    this.onSelected.subscribe(this.onInternalSelected);
  }

  ngOnInit(): void {
    this.init();
  }

  ngAfterViewInit(): void {

  }


  ngOnChanges(changes: SimpleChanges): void {
    if (changes.drop && !changes.drop.firstChange && changes.drop.previousValue !== changes.drop.currentValue) {
      this.reload(changes.drop.currentValue, this.take);
    }

    if (changes.take && !changes.take.firstChange && changes.take.previousValue !== changes.take.currentValue) {
      this.reload(this.drop, changes.take.currentValue);
    }
  }

  onChangePage($event) {
    this.drop = $event.pageSize * $event.pageIndex;
    this.take = $event.pageSize;
    this.load();
  }

  init() {
    this.load();
  }

  load() {
    this.petApi.pets(new PetsRequest(this.drop,
      this.take))
      .subscribe((response: PagedDto<PetsResponse>) => {
        this.model = response.results.map(item => new PetsListModel(item.id, item.name, item.petType, item.userLastName));
        this.resultsLength = response.totalCount;
      }, (_) => {
      });
  }

  reload(drop: number, take: number) {
    if (this.matPaginator) {
       this.matPaginator.pageIndex = 0;
     }
    this.drop = drop;
    this.take = take;
    this.load();
  }

  select(selectedId: number) {
    this.onSelected.emit(new Selected(selectedId));
  }

  onInternalSelected(event: Selected) {
    const item: PetsListModel = this.model.find(a => a.id === event.itemId);
    this.router.navigate(['/private/pet', item.id]);
  }

}

