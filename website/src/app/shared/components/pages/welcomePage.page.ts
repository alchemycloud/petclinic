import {SessionService} from '../../../services/session.service';
import {Footer} from '../containers/footer.container';
import {PublicHeader} from '../containers/publicHeader.container';
import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';

export class WelcomePageModel {


  constructor() {

  }

}

@Component({
  selector: 'welcome-page',
  templateUrl: './welcomePage.page.html',
  styleUrls: ['./welcomePage.page.scss']
})
export class WelcomePage implements OnInit, AfterViewInit {
  @ViewChild(PublicHeader)
  private readonly headerElement: PublicHeader;
  @ViewChild(Footer)
  private readonly footerElement: Footer;
  model: WelcomePageModel = new WelcomePageModel();

  constructor(private readonly route: ActivatedRoute, private readonly sessionService: SessionService, private readonly router: Router) {

  }

  ngOnInit(): void {
    this.init();
  }

  ngAfterViewInit(): void {

  }


  init() {

  }

}

