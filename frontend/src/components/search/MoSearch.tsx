import * as S from '../../styles/search/MoSearch.styled'
import SearchBar, { SearchResult } from './SearchBar'
import useIcon from '../../hooks/useIcon'
import { palette } from '../../constants/Styles'

interface MoSearchProps {
  setIsMoSearchOpen: (isOpen: boolean) => void
}

function MoSearch({ setIsMoSearchOpen }: MoSearchProps) {
  const { IcPrevLight } = useIcon()

  const closeMoSearch = () => {
    setIsMoSearchOpen(false)
  }

  const handleSearch = (results: SearchResult[]) => {
    console.log(`MoSearch에서 검색 결과:`, results)
  }

  return (
    <S.MoSearchContainer>
      <S.SearchHeader>
        <S.CloseButton type="button" onClick={closeMoSearch}>
          <IcPrevLight size={5} color={palette.textPrimary} />
        </S.CloseButton>
        <S.SearchHeaderTxt>
          <p>검색</p>
        </S.SearchHeaderTxt>
      </S.SearchHeader>
      <SearchBar onSearch={handleSearch} />
    </S.MoSearchContainer>
  )
}

export default MoSearch
