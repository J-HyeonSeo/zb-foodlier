import styled from 'styled-components'
import { breakpoints, palette } from '../../constants/Styles'

// 스타일드 컴포넌트
export const Container = styled.div`
  width: 100%;
`

export const Map = styled.div`
  width: 100%;
  height: 440px;
  background-color: teal;
`

export const SelectUserList = styled.div`
  width: 100%;
  padding: 20px 20px 0;
`

export const SelectTypeButton = styled.button`
  width: 50%;
  height: 50px;
  border-bottom: 1px solid ${palette.divider};
  font-size: 2rem;
  &:focus {
    font-weight: bold;
    border-bottom: 1px solid #000;
  }
`

export const ChefListContainer = styled.div`
  padding: 20px 20px 0 20px;
`
export const Info = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-bottom: 20px;
`
export const SubTitle = styled.span`
  font-weight: bold;
  font-size: 2rem;
`

export const SelectBox = styled.div`
  width: 150px;
  height: 100%;

  box-sizing: border-box;
  text-align: left;
`

export const SelectedBox = styled.div`
  position: relative;
  font-weight: bold;
  font-size: 2rem;
`

export const OptionList = styled.ul<{ $toggle: boolean }>`
  display: ${props => (props.$toggle ? 'block' : 'none')};
  width: 100%;
  position: absolute;
  top: 30px;
  left: 0;
  background-color: ${palette.white};
  border: 1px solid ${palette.divider};
  padding-left: 0;
  border-radius: 5px;
`

export const Option = styled.li`
  &:hover {
    background-color: ${palette.divider};
  }
`

export const OptionButton = styled.button`
  display: flex;
  justify-content: space-between;
  width: 100%;
  height: 100%;
  text-align: right;
  padding: 0 4px;
`

export const CardList = styled.ul`
  width: 100%;
  height: 500px;
  overflow: auto;
  padding-left: 0;
  &::-webkit-scrollbar {
    display: none;
  }
`

export const WritingButton = styled.button`
  width: 146px;
  height: 44px;
  line-height: 44px;
  text-align: center;
  background-color: #e45141;
  color: ${palette.white};
  border-radius: 5px;
`

export const ButtonList = styled.div`
  display: flex;
  justify-content: center;
  gap: 10px;
  border-top: 1px solid #d9d9d9d9;
  padding-top: 10px;
  ${breakpoints.large} {
    justify-content: end;
    padding: 10px 20px 0 0;
  }
  button {
    margin-bottom: 110px;
    ${breakpoints.large} {
      margin-bottom: 10px;
    }
  }
`