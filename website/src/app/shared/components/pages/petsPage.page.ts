import {UserRole} from '../../../services/backend/enums';
import {SessionService} from '../../../services/session.service';
import {Footer} from '../containers/footer.container';
import {PrivateHeader} from '../containers/privateHeader.container';
import {PetsList} from '../lists/petsList.list';
import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

export class PetsPageModel {


  constructor() {

  }

}

@Component({
  selector: 'pets-page',
  templateUrl: './petsPage.page.html',
  styleUrls: ['./petsPage.page.scss']
})
export class PetsPage implements OnInit, AfterViewInit {
  @ViewChild(PrivateHeader)
  private readonly headerElement: PrivateHeader;
  @ViewChild(PetsList)
  private readonly petsListElement: PetsList;
  @ViewChild(Footer)
  private readonly footerElement: Footer;
  model: PetsPageModel = new PetsPageModel();

  constructor(private readonly router: Router, private readonly route: ActivatedRoute, private readonly sessionService: SessionService) {
    if (!([UserRole.VET].includes(sessionService.getSessionData().role)))
      this.router.navigate(['/welcome-page']);
  }

  ngOnInit(): void {
    this.init();
  }

  ngAfterViewInit(): void {

  }


  init() {

  }

  onAddPetClick() {
    this.router.navigate(['/private/pet/new']);
  }

}

